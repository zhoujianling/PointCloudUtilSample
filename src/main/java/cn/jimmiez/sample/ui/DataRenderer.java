package cn.jimmiez.sample.ui;

import cn.jimmiez.pcu.alg.skeleton.Skeleton;
import cn.jimmiez.pcu.common.graph.Graphs;
import cn.jimmiez.pcu.common.graphics.BoundingBox;
import cn.jimmiez.pcu.model.Pair;
import com.sun.j3d.utils.geometry.Sphere;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataRenderer {

    public Shape3D pointsShape(List<Point3d> points) {
        Shape3D shape = new Shape3D();
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        PointArray pa = new PointArray(points.size(), PointArray.COORDINATES);
        Point3d[] pointArray = new Point3d[points.size()];
        for (int i = 0; i < points.size(); i ++) {
            pointArray[i] = points.get(i);
        }
        pa.setCoordinates(0, pointArray);
        shape.setGeometry(pa);
        Appearance ap = new Appearance();

        ColoringAttributes ca = new ColoringAttributes();

        ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ);
        ca.setColor(.5f, .5f, .5f);

        ap.setColoringAttributes(ca);
        ap.setMaterial(null);
        ap.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_POINT, PolygonAttributes.CULL_BACK, 0));
        ap.setPointAttributes(new PointAttributes(3, false));
        shape.setAppearance(ap);

        return shape;
    }

    public BranchGroup skeletonShape(Skeleton skeleton) {
        BranchGroup bg = new BranchGroup();
        Shape3D lineShape = new Shape3D();
        int edgeCnt = 2 * Graphs.edgesCountOf(skeleton);
        LineArray lineArray = new LineArray(edgeCnt, LineArray.COORDINATES);
        Point3d[] ends = new Point3d[edgeCnt];
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        int cnt = 0;
        for (Integer vertexIndex : skeleton.vertices()) {
            for (Integer adjacentVertexIndex : skeleton.adjacentVertices(vertexIndex)) {
                if (! set.contains(new Pair<>(vertexIndex, adjacentVertexIndex))) {
                    ends[cnt ++] = skeleton.getSkeletonNodes().get(vertexIndex);
                    ends[cnt ++] = skeleton.getSkeletonNodes().get(adjacentVertexIndex);
                    set.add(new Pair<>(vertexIndex, adjacentVertexIndex));
                    set.add(new Pair<>(adjacentVertexIndex, vertexIndex));
                }
            }
        }
        lineArray.setCoordinates(0, ends);
        Appearance ap = new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(0.0f, 1.0f, 0.f);
        ap.setColoringAttributes(ca);
        ap.setMaterial(null);
        ap.setLineAttributes(new LineAttributes(8, LineAttributes.PATTERN_SOLID, false));
        lineShape.setAppearance(ap);
        lineShape.setGeometry(lineArray);

        Appearance ap2 = new Appearance();

        ColoringAttributes ca2 = new ColoringAttributes();

        ap2.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ);
        ca2.setColor(1f, .0f, .0f);

        ap2.setColoringAttributes(ca2);
        ap2.setMaterial(null);

        bg.addChild(lineShape);

        Transform3D transform3D = new Transform3D();
        for (Point3d point : skeleton.getSkeletonNodes()) {
            TransformGroup tg = new TransformGroup();
            transform3D.set(new Vector3d(point.x, point.y, point.z));
            Sphere nodeShape = new Sphere(0.009f );
            nodeShape.setAppearance(ap2);
            tg.addChild(nodeShape);
            tg.setTransform(transform3D);
            bg.addChild(tg);
        }
        return bg;
    }
}
