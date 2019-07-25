package cn.jimmiez.sample.demo;

import cn.jimmiez.pcu.common.graphics.Octree;
import cn.jimmiez.pcu.io.ply.PlyReader;
import cn.jimmiez.pcu.model.PointCloud3f;
import cn.jimmiez.sample.model.PointCloud;
import cn.jimmiez.sample.ui.DataRenderer;
import cn.jimmiez.sample.ui.PointCloudFrame;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowNeighbors {

    public static void main(String[] args) {
        File plyFile = new File(ShowPointCloud.class.getClassLoader().getResource("Eagle.ply").getFile());
        List<float[]> points = new PlyReader().read(plyFile, PointCloud3f.class).getPoints();
        PointCloud pointCloud = new PointCloud(points);
        Octree4Ui octree = new Octree4Ui();
        octree.setMaxPointsPerNode(200);
        octree.buildIndex(pointCloud.getPoints());

        int searchCenterIndex = 2500;
        int numberOfNeighbors = 1200;
        int[] neighbors = octree.searchNearestNeighbors(numberOfNeighbors, searchCenterIndex);
        List<Point3d> neighboringPoints = new ArrayList<>();
        List<Point3d> searchCenter = new ArrayList<>();
        for (int neighborIndex : neighbors) {
            Point3d neighboringPoint = pointCloud.getPoints().get(neighborIndex);
            neighboringPoints.add(neighboringPoint);
        }
        searchCenter.add(pointCloud.getPoints().get(searchCenterIndex));
        DataRenderer renderer = new DataRenderer();

        List<Octree.OctreeNode> nodes = new ArrayList<>(octree.getNodes().values());
        PointCloudFrame frame = new PointCloudFrame();
        frame.getExtraBranchGroups().add(renderer.octreeShape(nodes));
        frame.getExtraBranchGroups().add(renderer.pointsShape(neighboringPoints, new Color3f(1.0f, 0.f, 0.f), 2));
        frame.getExtraBranchGroups().add(renderer.pointsShape(searchCenter, new Color3f(0.0f, 1.f, 0.f), 5));
        frame.init(pointCloud);


    }

    static class Octree4Ui extends Octree {
        Map<Long, OctreeNode> getNodes() {return super.octreeIndices;}}
}
