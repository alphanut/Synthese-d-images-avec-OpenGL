﻿// Définition de la classe PrimitiveRestart

class PrimitiveRestart
{
    /**
     * Constructeur
     */
    constructor()
    {
        /** shader */

        let srcVertexShader = dedent
            `#version 300 es
            in vec2 glVertex;
            in vec3 glColor;
            out vec3 frgColor;
            void main()
            {
                gl_Position = vec4(glVertex*0.4, 0.0, 1.0);
                frgColor = glColor;
            }`;

        let srcFragmentShader = dedent
            `#version 300 es
            precision mediump float;
            in vec3 frgColor;
            out vec4 glFragColor;
            void main()
            {
                glFragColor = vec4(frgColor, 1.0);
            }`;

        // compiler le shader de dessin
        this.m_ShaderId = Utils.makeShaderProgram(srcVertexShader, srcFragmentShader, "PrimitiveRestart");

        // déterminer où sont les variables attribute
        this.m_VertexLoc = gl.getAttribLocation(this.m_ShaderId, "glVertex");
        this.m_ColorLoc = gl.getAttribLocation(this.m_ShaderId, "glColor");

        /** VBOs */

        // créer et remplir le buffer des coordonnées
        let vertices = [
             -1.5, -1.5,    // P0
              1.0, -2.0,    // P1
             -2.0, -0.5,    // P2
              2.0,  0.0,    // P3
             -1.0,  1.0,    // P4
              1.0,  1.5,    // P5
             -1.5,  2.0,    // P6
              1.5,  2.0     // P7
        ];
        this.m_VertexBufferId = Utils.makeFloatVBO(vertices, gl.ARRAY_BUFFER, gl.STATIC_DRAW);

        // créer et remplir le buffer des couleurs
        let colors = [
            1.0, 0.11, 0.11,
            1.0, 0.71, 0.11,
            0.71, 1.0, 0.11,
            0.11, 1.0, 0.11,
            0.11, 1.0, 0.72,
            0.11, 0.72, 1.0,
            0.11, 0.11, 1.0,
            0.72, 0.11, 1.0
        ];
        this.m_ColorBufferId = Utils.makeFloatVBO(colors, gl.ARRAY_BUFFER, gl.STATIC_DRAW);

        // créer et remplir le buffer des indices
        let indexlist = [
            0, 1, 2, 3, 3, 4, 4, 5, 6, 7
        ];
        this.m_IndexBufferSize = indexlist.length;
        this.m_IndexBufferId = Utils.makeShortVBO(indexlist, gl.ELEMENT_ARRAY_BUFFER, gl.STATIC_DRAW);
    }


    onDraw()
    {
        // activer le shader
        gl.useProgram(this.m_ShaderId);

        // activer et lier le buffer contenant les coordonnées
        gl.bindBuffer(gl.ARRAY_BUFFER, this.m_VertexBufferId);
        gl.enableVertexAttribArray(this.m_VertexLoc);
        gl.vertexAttribPointer(this.m_VertexLoc, Utils.VEC2, gl.FLOAT, gl.FALSE, 0, 0);

        // activer et lier le buffer contenant les couleurs
        gl.bindBuffer(gl.ARRAY_BUFFER, this.m_ColorBufferId);
        gl.enableVertexAttribArray(this.m_ColorLoc);
        gl.vertexAttribPointer(this.m_ColorLoc, Utils.VEC3, gl.FLOAT, gl.FALSE, 0, 0);

        // activer et lier le buffer contenant les indices
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, this.m_IndexBufferId);

        // dessiner un ruban, sachant qu'il y a des coupures dedans
        gl.drawElements(gl.TRIANGLE_STRIP, this.m_IndexBufferSize, gl.UNSIGNED_SHORT, 0);

        // désactiver les buffers
        gl.disableVertexAttribArray(this.m_VertexLoc);
        gl.disableVertexAttribArray(this.m_ColorLoc);
        gl.bindBuffer(gl.ARRAY_BUFFER, null);
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, null);

        // désactiver le shader
        gl.useProgram(null);
    }


    /**
     * Cette méthode supprime les ressources allouées
     */
    destroy()
    {
        Utils.deleteVBO(this.m_IndexBufferId);
        Utils.deleteVBO(this.m_ColorBufferId);
        Utils.deleteVBO(this.m_VertexBufferId);
        Utils.deleteShaderProgram(this.m_ShaderId);
    }
}
