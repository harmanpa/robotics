package tech.cae.jurdf.mappers;

import tech.cae.configurators.model.mechanisms.Body;

public class Mass {
    public static tech.cae.robotics.urdf.Mass convertMass(Body body) {
        if (body.getMass() == null) {
            return null;
        }
        tech.cae.robotics.urdf.Mass mass = new tech.cae.robotics.urdf.Mass();
        mass.setValue(body.getMass().getMass().doubleValue());
        return mass;
    }
}
