package cn.jimmiez.sample.shape;

import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Vector3f;

/**
 * a cube used for visualizing octree voxel
 * @author Ji Liu, College of Computer Science, Chongqing University
 */
public class Cube extends Shape3D {

    private static final float[] verts = {
            // front face
            1.0f, -1.0f,  1.0f,
            1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f,
            -1.0f, -1.0f,  1.0f,
            // back face
            -1.0f, -1.0f, -1.0f,
            -1.0f,  1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            // right face
            1.0f, -1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            1.0f,  1.0f,  1.0f,
            1.0f, -1.0f,  1.0f,
            // left face
            -1.0f, -1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            // top face
            1.0f,  1.0f,  1.0f,
            1.0f,  1.0f, -1.0f,
            -1.0f,  1.0f, -1.0f,
            -1.0f,  1.0f,  1.0f,
            // bottom face
            -1.0f, -1.0f,  1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f,  1.0f,
    };

    private static final Vector3f[] normals = {
            new Vector3f( 0.0f,  0.0f,  1.0f),	// front face
            new Vector3f( 0.0f,  0.0f, -1.0f),	// back face
            new Vector3f( 1.0f,  0.0f,  0.0f),	// right face
            new Vector3f(-1.0f,  0.0f,  0.0f),	// left face
            new Vector3f( 0.0f,  1.0f,  0.0f),	// top face
            new Vector3f( 0.0f, -1.0f,  0.0f),	// bottom face
    };

    public Cube() {
        super();

        int i;

        QuadArray cube = new QuadArray(24, QuadArray.COORDINATES |
                QuadArray.NORMALS);

        cube.setCoordinates(0, verts);
        for (i = 0; i < 24; i++) {
            cube.setNormal(i, normals[i/4]);
        }

        setGeometry(cube);
    }
}

