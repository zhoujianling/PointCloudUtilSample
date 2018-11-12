package cn.jimmiez.sample.model;

import cn.jimmiez.pcu.common.graphics.BoundingBox;
import cn.jimmiez.pcu.common.graphics.NormalEstimator;
import cn.jimmiez.pcu.util.PcuCommonUtil;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.util.List;
import java.util.Vector;

public class PointCloud {

    /** data points in point cloud **/
    private List<Point3d> points = null;

    /** estimated normals of each point in point cloud **/
    private List<Vector3d> normals = null;

    private BoundingBox box = null;

    public PointCloud(List<float[]> points3f) {
        points = PcuCommonUtil.arrayList2VecList(points3f);
        box = BoundingBox.of(points);
    }

    public <T extends NormalEstimator> void estimateNormal(T estimator) {
        System.out.println("Estimating normals, waiting ...");
        normals = estimator.estimateNormals(points);
        if (normals.size() != points.size()) throw new IllegalStateException("normals.size() != points.size()");
    }

    private Shape3D pointsShape() {
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

    private Shape3D normalShape() {
        Shape3D lines = new Shape3D();
        double len = box.diagonalLength() / 100;
        LineArray lineArray = new LineArray(2 * points.size(), LineArray.COORDINATES);
        Point3d[] ends = new Point3d[2 * points.size()];
        for (int i = 0; i < points.size(); i ++) {
            ends[2 * i] = points.get(i);
//            System.out.println("ends[i].x: " + ends[i].x);
            Vector3d normal = new Vector3d(normals.get(i));
            normal.scale(len);
            ends[2 * i + 1] = new Point3d(ends[2 * i].x + normal.x, ends[2 * i].y + normal.y, ends[2 * i].z + normal.z);
        }
        lineArray.setCoordinates(0, ends);
        Appearance ap = new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(0.66f, 0.82f, 0.55f);
        ap.setColoringAttributes(ca);
        ap.setMaterial(null);
//        ap.setPointAttributes(new PointAttributes(config.getSkeletonCurveWidth(), false));
        ap.setLineAttributes(new LineAttributes(2, LineAttributes.PATTERN_SOLID, false));
        lines.setAppearance(ap);
        lines.setGeometry(lineArray);
        return lines;
    }

    public BranchGroup branchGroup() {
        BranchGroup bg = new BranchGroup();
        bg.addChild(pointsShape());
        bg.addChild(normalShape());
        return bg;
    }
}
