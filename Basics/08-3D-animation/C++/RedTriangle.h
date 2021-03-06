#ifndef TRIANGLEROUGE_H
#define TRIANGLEROUGE_H

// Définition de la classe RedTriangle

#include <gl-matrix.h>

class RedTriangle
{
private:

    /** tableau des coordonnées, x,y,z à la suite */
    GLuint m_VertexBufferId;

    /** identifiants liés au shader */
    GLuint m_ShaderId;
    GLuint m_MatrixLoc;
    GLuint m_VertexLoc;

public:

    /** constructeur, crée le VBO et le shader */
    RedTriangle() throw (std::string);

    /** destructeur, libère le VBO et le shader */
    ~RedTriangle();

    /**
     * dessiner l'objet
     * @param matrix : matrice à appliquer sur l'objet
     */
    void onDraw(mat4 matrix);
};

#endif
