package cn.jimmiez.sample.model;

import cn.jimmiez.pcu.alg.normal.NormalEstimator;
import cn.jimmiez.pcu.alg.projector.OctreeVoxelizer;
import cn.jimmiez.pcu.alg.skeleton.Skeleton;
import cn.jimmiez.pcu.alg.skeleton.Skeletonization;
import cn.jimmiez.pcu.common.graph.GraphStatic;
import cn.jimmiez.pcu.common.graphics.BoundingBox;
import cn.jimmiez.pcu.common.graphics.Octree;
import cn.jimmiez.pcu.util.PcuCommonUtil;
import cn.jimmiez.sample.ui.DataRenderer;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.util.*;

public class PointCloud {

    /** data points in point cloud **/
    private List<Point3d> points = null;

    /** estimated normals of each point in point cloud **/
    private List<Vector3d> normals = null;

    /** the curve skeleton of point cloud model **/
    private Skeleton skeleton = null;

    /** k-nearest-neighbor graph **/
    private GraphStatic neighborhoodGraph = null;

    /** the voxelized result **/
    private List<Octree.OctreeNode> voxels = null;

    private BoundingBox box = null;

    public PointCloud(List<float[]> points3f) {
        points = PcuCommonUtil.arrayList2VecList(points3f);
        box = BoundingBox.of(points);
    }

    public void voxelize(OctreeVoxelizer voxelizer) {
        System.out.println("Voxelizing ... Please wait ...");
        voxels = voxelizer.voxelize(points, 6);
    }

    public <T extends NormalEstimator> void estimateNormal(T estimator) {
        System.out.println("Estimating normals ... Please wait ...");
        normals = estimator.estimateNormals(points);
        if (normals.size() != points.size()) throw new IllegalStateException("normals.size() != points.size()");
    }

    public <T extends Skeletonization> void skeletonize(T skeletonExtractor) {
        System.out.println("Extracting curve skeleton ... Please wait ..");
        skeleton = skeletonExtractor.skeletonize(points);
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
        ap.setLineAttributes(new LineAttributes(2, LineAttributes.PATTERN_SOLID, false));
        lines.setAppearance(ap);
        lines.setGeometry(lineArray);
        return lines;
    }



    public BranchGroup branchGroup() {
        BranchGroup bg = new BranchGroup();
        DataRenderer renderer = new DataRenderer();
        if (points != null) {
            bg.addChild(renderer.pointsShape(points));
        }
        if (normals != null) {
            bg.addChild(normalShape());
        }
        if (skeleton != null) {
            bg.addChild(renderer.skeletonShape(skeleton));
        }
        if (neighborhoodGraph != null) {
            bg.addChild(renderer.graphShape(points, neighborhoodGraph));
        }
        if (voxels != null) {
            bg.addChild(renderer.octreeShape(voxels));
        }
        return bg;
    }
}
