﻿// Définition de la classe Tetraedre


class Tetraedre
{
    /** constructeur */
    constructor()
    {
        /** shader */

        let srcVertexShader = dedent
            `#version 300 es
            uniform mat4 matrix;
            in vec3 glVertex;
            in vec3 glColor;
            out vec3 frgColor;
            void main()
            {
                gl_Position = matrix * vec4(glVertex, 1.0);
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
        this.m_ShaderId = Utils.makeShaderProgram(srcVertexShader, srcFragmentShader, "Tetraedre");

        // déterminer où sont les variables attribute et uniform
        this.m_MatrixLoc = gl.getUniformLocation(this.m_ShaderId, "matrix");
        this.m_VertexLoc = gl.getAttribLocation(this.m_ShaderId, "glVertex");
        this.m_ColorLoc = gl.getAttribLocation(this.m_ShaderId, "glColor");

        /** VBOs */

        // créer et remplir le buffer des coordonnées
        const b = 0.5;
        let vertices = [
            -b, +b, +b,     // P0 bleu
            +b, -b, +b,     // P1 orange
            +b, +b, -b,     // P2 vert
            -b, -b, -b,     // P3 violet
        ];
        this.m_VertexBufferId = Utils.makeFloatVBO(vertices, gl.ARRAY_BUFFER, gl.STATIC_DRAW);

        // créer et remplir le buffer des couleurs
        let colors = [
            0.0, 0.5, 1.0,  // P0 bleu
            1.0, 0.5, 0.0,  // P1 orange
            0.0, 1.0, 0.0,  // P2 vert
            0.7, 0.0, 0.7,  // P3 violet
        ];
        this.m_ColorBufferId = Utils.makeFloatVBO(colors, gl.ARRAY_BUFFER, gl.STATIC_DRAW);

        // créer et remplir le buffer des indices
        let indexlist = [
            0, 1,
            0, 2,
            0, 3,
            1, 2,
            1, 3,
            2, 3,
        ];
        this.LINE_COUNT = indexlist.length / 2;
        this.m_IndexBufferId = Utils.makeShortVBO(indexlist, gl.ELEMENT_ARRAY_BUFFER, gl.STATIC_DRAW);
    }


    /**
     * dessiner l'objet
     * @param matrix : matrice à appliquer sur l'objet
     */
    onDraw(matrix)
    {
        // activer le shader
        gl.useProgram(this.m_ShaderId);

        // fournir la matrice au shader
        mat4.glUniformMatrix(this.m_MatrixLoc, matrix);

        // activer et lier le buffer contenant les coordonnées
        gl.bindBuffer(gl.ARRAY_BUFFER, this.m_VertexBufferId);
        gl.enableVertexAttribArray(this.m_VertexLoc);
        gl.vertexAttribPointer(this.m_VertexLoc, Utils.VEC3, gl.FLOAT, gl.FALSE, 0, 0);

        // activer et lier le buffer contenant les couleurs
        gl.bindBuffer(gl.ARRAY_BUFFER, this.m_ColorBufferId);
        gl.enableVertexAttribArray(this.m_ColorLoc);
        gl.vertexAttribPointer(this.m_ColorLoc, Utils.VEC3, gl.FLOAT, gl.FALSE, 0, 0);

        // activer et lier le buffer contenant les indices
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, this.m_IndexBufferId);

        // dessiner les lignes
        gl.lineWidth(3.0);
        gl.drawElements(gl.LINES, this.LINE_COUNT * 2, gl.UNSIGNED_SHORT, 0);

        // désactiver les buffers
        gl.disableVertexAttribArray(this.m_VertexLoc);
        gl.disableVertexAttribArray(this.m_ColorLoc);
        gl.bindBuffer(gl.ARRAY_BUFFER, null);
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, null);

        // désactiver le shader
        gl.useProgram(null);
    }


    /** destructeur */
    destroy()
    {
        // supprimer le shader et les VBOs
        Utils.deleteShaderProgram(this.m_ShaderId);
        Utils.deleteVBO(this.m_VertexBufferId);
        Utils.deleteVBO(this.m_ColorBufferId);
        Utils.deleteVBO(this.m_IndexBufferId);
    }
}
