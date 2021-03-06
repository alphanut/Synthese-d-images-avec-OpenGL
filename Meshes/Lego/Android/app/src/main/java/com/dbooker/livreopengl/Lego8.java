package com.dbooker.livreopengl;


import java.util.ArrayList;
import java.util.Arrays;

import com.dbooker.livreopengl.glmatrix.vec3;
import com.dbooker.livreopengl.mesh.Mesh;
import com.dbooker.livreopengl.mesh.MeshModuleProcessing;
import com.dbooker.livreopengl.mesh.MeshVertex;

public class Lego8
{
    private Mesh m_Mesh;

    public Lego8() throws Exception
    {
        vec3 color1 = vec3.fromValues(0.0,0.3,0.8);
        vec3 color2 = vec3.fromValues(0.1,0.5,1.0);

        // créer l'objet maillage
        m_Mesh = new Mesh();

        /// Topologie : éléments et connexions

        // rajouter des sommets
        MeshVertex A = m_Mesh.addVertex("A");
        MeshVertex B = m_Mesh.addVertex("B");
        MeshVertex C = m_Mesh.addVertex("C");
        MeshVertex D = m_Mesh.addVertex("D");
        MeshVertex E = m_Mesh.addVertex("E");
        MeshVertex F = m_Mesh.addVertex("F");
        MeshVertex G = m_Mesh.addVertex("G");
        MeshVertex H = m_Mesh.addVertex("H");
        MeshVertex I = m_Mesh.addVertex("I");
        MeshVertex J = m_Mesh.addVertex("J");
        MeshVertex K = m_Mesh.addVertex("K");
        MeshVertex L = m_Mesh.addVertex("L");
        MeshVertex M = m_Mesh.addVertex("M");
        MeshVertex N = m_Mesh.addVertex("N");
        MeshVertex O = m_Mesh.addVertex("O");
        MeshVertex P = m_Mesh.addVertex("P");

        // rajouter des triangles et des quads
        m_Mesh.addTriangle(A, G, E);
        m_Mesh.addQuad(A, E, F, B);
        m_Mesh.addTriangle(B, F, H);
        m_Mesh.addQuad(B, H, J, D);
        m_Mesh.addTriangle(D, J, L);
        m_Mesh.addQuad(D, L, K, C);
        m_Mesh.addTriangle(C, K, I);
        m_Mesh.addQuad(C, I, G, A);

        //m_Mesh.addQuad(G, I, F, E);
        //m_Mesh.addQuad(I, K, H, F);
        //m_Mesh.addQuad(K, L, J, H);

        m_Mesh.addTriangle(G, M, E);
        m_Mesh.addQuad(E, M, N, F);
        m_Mesh.addTriangle(F, N, H);
        m_Mesh.addQuad(H, N, P, J);
        m_Mesh.addTriangle(J, P, L);
        m_Mesh.addQuad(L, P, O, K);
        m_Mesh.addTriangle(K, O, I);
        m_Mesh.addQuad(I, O, M, G);

        m_Mesh.addQuad(M, O, P, N);

        /// Géométrie : position des sommets et autres info

        // affectation des couleurs et des coordonnées des sommets
        A.setCoord(vec3.fromValues(-3.0, 0.0,-3.0)); A.setColor(color1);
        B.setCoord(vec3.fromValues( 3.0, 0.0,-3.0)); B.setColor(color1);
        C.setCoord(vec3.fromValues(-3.0, 0.0, 3.0)); C.setColor(color1);
        D.setCoord(vec3.fromValues( 3.0, 0.0, 3.0)); D.setColor(color1);

        E.setCoord(vec3.fromValues(-1.0, 0.0,-2.0)); E.setColor(color1);
        F.setCoord(vec3.fromValues( 1.0, 0.0,-2.0)); F.setColor(color1);

        G.setCoord(vec3.fromValues(-2.0, 0.0,-1.0)); G.setColor(color1);
        H.setCoord(vec3.fromValues( 2.0, 0.0,-1.0)); H.setColor(color1);
        I.setCoord(vec3.fromValues(-2.0, 0.0, 1.0)); I.setColor(color1);
        J.setCoord(vec3.fromValues( 2.0, 0.0, 1.0)); J.setColor(color1);

        K.setCoord(vec3.fromValues(-1.0, 0.0, 2.0)); K.setColor(color1);
        L.setCoord(vec3.fromValues( 1.0, 0.0, 2.0)); L.setColor(color1);

        M.setCoord(vec3.fromValues(-1.0, 0.0,-1.0)); M.setColor(color2);
        N.setCoord(vec3.fromValues( 1.0, 0.0,-1.0)); N.setColor(color2);
        O.setCoord(vec3.fromValues(-1.0, 0.0, 1.0)); O.setColor(color2);
        P.setCoord(vec3.fromValues( 1.0, 0.0, 1.0)); P.setColor(color2);


        // calculer les normales
        m_Mesh.computeNormals();

        // module de transformation pour ce maillage
        MeshModuleProcessing transformation = new MeshModuleProcessing(m_Mesh);

        // faire une extrusion de la bosse
        ArrayList<MeshVertex> edge1 = new ArrayList<MeshVertex>();
        edge1.add(G); edge1.add(I); edge1.add(K); edge1.add(L); edge1.add(J); edge1.add(H); edge1.add(F); edge1.add(E);
        ArrayList<MeshVertex> clones = transformation.extrudePolygon(edge1, 2.5f);

        transformation.splitBorder(edge1);
        transformation.splitBorder(clones);

        // sous-extrusion du centre de la bosse
        ArrayList<MeshVertex> edge2 = new ArrayList<MeshVertex>();
        edge2.add(M); edge2.add(O); edge2.add(P); edge2.add(N);
        transformation.extrudePolygon(edge2, -1.0f);

        // calculer les normales
        m_Mesh.computeNormals();
    }


    /**
     * fournit le maillage de cet objet
     * @return maillage
     */
    public Mesh getMesh()
    {
        return m_Mesh;
    }


    /**
     * Cette méthode supprime les ressources allouées
     */
    public void destroy()
    {
        m_Mesh.destroy();
    }
}
