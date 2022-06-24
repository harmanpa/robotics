package tech.cae.jurdf.mappers;

import tech.cae.configurators.model.mechanisms.Mechanism;

import java.util.List;

public class Robot {
    static public tech.cae.robotics.urdf.Robot convertOnShapeMechanismToRobot(Mechanism mechanism) {
        tech.cae.robotics.urdf.Robot robot = new tech.cae.robotics.urdf.Robot();
        //TODO: Hardcoded name
        robot.setName("Robot");
        setRobotLinks(robot, mechanism);
        setRobotJoints(robot, mechanism);
        return robot;
    }

    static public void setRobotJoints(tech.cae.robotics.urdf.Robot robot, Mechanism mechanism) {
        List<tech.cae.robotics.urdf.Joint> robotJoints = Joints.convertMechanismJointsToUrdfJoints(mechanism.getJoints());
        for (tech.cae.robotics.urdf.Joint joint : robotJoints) {
            robot.getJointsAndLinksAndMaterials().add(joint);
        }
    }

    static public void setRobotLinks(tech.cae.robotics.urdf.Robot robot, Mechanism mechanism) {
        List<tech.cae.robotics.urdf.Link> robotLinks = Links.convertMechanismBodiesToUrdfLinks(mechanism.getBodies());
        for (tech.cae.robotics.urdf.Link link : robotLinks) {
            robot.getJointsAndLinksAndMaterials().add(link);
        }
    }
}
