package com.dbooker.livreopengl;

import static android.opengl.GLES20.*;

public class Scene
{
    // objets de la scène
    private RedTriangle m_RedTriangle;
    private GreenTriangle m_GreenTriangle;


    /** créer les objets et initialise OpenGL */
    public Scene() throws Exception
    {
        // créer les objets à dessiner
        m_RedTriangle = new RedTriangle();
        m_GreenTriangle = new GreenTriangle();

        // couleur du fond : gris foncé
        glClearColor(0.4f, 0.4f, 0.4f, 0.0f);
    }


    /**
     * appelée quand la taille de la vue OpenGL change
     * @param width : largeur en nombre de pixels de la fenêtre
     * @param height : hauteur en nombre de pixels de la fenêtre
     */
    public void onSurfaceChanged(int width, int height)
    {
        // met en place le viewport
        glViewport(0, 0, width, height);
    }


    /**
     * Dessine l'image courante
     */
    public void onDrawFrame()
    {
        // effacer l'écran
        glClear(GL_COLOR_BUFFER_BIT);

        // dessiner les triangles
        m_RedTriangle.onDraw();
        m_GreenTriangle.onDraw();
    }


    /** supprime tous les objets de cette scène */
    public void destroy()
    {
        m_RedTriangle.destroy();
        m_GreenTriangle.destroy();
    }
}
