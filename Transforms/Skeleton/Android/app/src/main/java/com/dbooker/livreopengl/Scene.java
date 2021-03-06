package com.dbooker.livreopengl;

import static android.opengl.GLES20.*;

import com.dbooker.livreopengl.mesh.MeshObject;
import com.dbooker.livreopengl.mesh.MeshObjectFromObj;
import com.dbooker.livreopengl.misc.TurnTableScene;
import com.dbooker.livreopengl.glmatrix.mat4;
import com.dbooker.livreopengl.glmatrix.vec3;
import com.dbooker.livreopengl.glmatrix.vec4;
import com.dbooker.livreopengl.material.OmniLight;
import com.dbooker.livreopengl.utils.Utils;


public class Scene extends TurnTableScene
{
    // objets de la scène
    private Cow m_Cow;
    private MeshObject m_Grass;
    private OmniLight m_Light0;
    private OmniLight m_Light1;


    /** créer les objets et initialise OpenGL */
    public Scene() throws Exception
    {
        super(true);

        // définir une lampe directionnelle
        m_Light0 = new OmniLight();
        m_Light0.setPosition(vec4.fromValues(1, 1, -1, 0));
        m_Light0.setColor(vec3.fromValues(1.0,1.0,1.0));
        addLight(m_Light0);

        // définir une lampe positionnelle
        m_Light1 = new OmniLight();
        m_Light1.setPosition(vec4.fromValues(-10, 5, 5, 1));
        m_Light1.setColor(vec3.fromValues(150,150,150));
        addLight(m_Light1);

        // créer les objets à dessiner
        m_Cow = new Cow();
        m_Grass = new MeshObjectFromObj("data/models/TerrainSimple", "Terrain.obj", "TerrainHerbe.mtl", 3.0f);

        // configurer les modes de dessin
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        // couleur du fond
        glClearColor(0.4f, 0.4f, 0.4f, 0.0f);

        // paramètres de la vue
        m_CameraAngleAzimut = 50.0f;
        m_CameraAngleElevation = 10.0f;
        vec3.set(m_CameraPivot, 0.0f, -0.9f, 0.0f);
        m_CameraDistance = 7;
    }


    /**
     * appelée quand la taille de la vue OpenGL change
     * @param width : largeur en nombre de pixels de la fenêtre
     * @param height : hauteur en nombre de pixels de la fenêtre
     */
    @Override
    public void onSurfaceChanged(int width, int height)
    {
        // appeler la méthode de la superclasse
        super.onSurfaceChanged(width, height);

        // matrice de projection (champ de vision)
        mat4.perspective(m_Mat4Projection, Utils.radians(18.0), (float)width / height, 0.1f, 20.0f);
    }


    /**
     * dessine les objets de la scène
     * @param mat4Projection : matrice de projection
     * @param mat4ModelView : matrice de vue
     */
    @Override
    public void onDraw(mat4 mat4Projection, mat4 mat4ModelView)
    {
        // dessiner les objets
        m_Grass.onDraw(mat4Projection, mat4ModelView);
        m_Cow.onDraw(mat4Projection, mat4ModelView);
    }


    /**
     * Cette méthode supprime les ressources allouées
     */
    @Override
    public void destroy()
    {
        m_Light1.destroy();
        m_Light0.destroy();
        m_Grass.destroy();
        m_Cow.destroy();
        super.destroy();
    }
}
