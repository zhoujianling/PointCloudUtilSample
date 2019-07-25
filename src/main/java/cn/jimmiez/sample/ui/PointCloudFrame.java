package cn.jimmiez.sample.ui;

import cn.jimmiez.sample.model.PointCloud;

import javax.media.j3d.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PointCloudFrame extends JFrame {

    private List<Node> extraBranchGroups = new ArrayList<>();

    public void init(PointCloud pointCloud) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new Dimension((int)(screenSize.height * 0.75), (int)(screenSize.height * 0.75)));
        BranchGroup branchGroups = createScene(pointCloud);
        for (Node bg : extraBranchGroups) branchGroups.addChild(bg);
        add(new MainCanvas(branchGroups));
        setVisible(true);
    }

    private BranchGroup createScene(PointCloud pointCloud) {
        BranchGroup bg = new BranchGroup();
        bg.addChild(pointCloud.branchGroup());
        return bg;
    }

    public List<Node> getExtraBranchGroups() {return this.extraBranchGroups;}
}
