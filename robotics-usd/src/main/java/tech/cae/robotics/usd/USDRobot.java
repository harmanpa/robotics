package tech.cae.robotics.usd;

import lombok.Getter;
import lombok.Setter;
import tech.cae.robotics.urdf.*;

import java.util.List;

@Getter
@Setter
public class USDRobot {
    public static String html;
    public static String meshesHtml;
    public USDRobot(){};
    protected String physicsScene;
    protected String mass;
    protected String mesh;
    protected String meshAttribute;
    protected String joint;
    protected String parentJoint;
    protected String childJoint;
    protected String sphereAttribute;
    protected String childPrim;
    protected String lowerLimit;
    protected String upperLimit;
    protected String XYZAxis;
    protected String RPYAxis;
    protected String jointAxis;
    protected String inertiaAttribute;
    protected String massAttribute;
    protected Inertia inertia;
    protected String originXYZ;
    protected String originRPY;
    protected boolean isRigidBody = false;
    protected String collisionMesh;
    protected String collisionOriginXYZ;
    protected String collisionOriginRPY;
    protected List<Visual> visualList;
    protected boolean isRevoluteJoint = false;
    protected List<Integer> integers;

}
