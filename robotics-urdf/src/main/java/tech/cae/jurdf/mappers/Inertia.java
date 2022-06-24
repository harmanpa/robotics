package tech.cae.jurdf.mappers;

import tech.cae.configurators.model.mechanisms.Body;

public class Inertia {
    public static tech.cae.robotics.urdf.Inertia convertInertia(Body body) {
        if (body.getMass() == null) {
            return null;
        }

        Number[] inertia = body.getMass().getInertia();
        tech.cae.robotics.urdf.Inertia urdfInertia = new tech.cae.robotics.urdf.Inertia();
        urdfInertia.setIxx(inertia[0].doubleValue());
        urdfInertia.setIxy(inertia[1].doubleValue());
        urdfInertia.setIxz(inertia[2].doubleValue());
        urdfInertia.setIyy(inertia[3].doubleValue());
        urdfInertia.setIyz(inertia[4].doubleValue());
        urdfInertia.setIzz(inertia[5].doubleValue());
        return urdfInertia;
    }
}
