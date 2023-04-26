package tech.cae.robotics.usd;

import jep.Interpreter;
import jep.SubInterpreter;
import tech.cae.robotics.urdf.*;

import java.io.File;

public class USDRobot {

    // should have variables with python actions assign:
    // @SomeAnnotation("stage = Usd.Stage.CreateNew("fileName.usd")")
    // String stage;

    private final Interpreter interpreter = new SubInterpreter();

    public static File tempDir = new File("temp.py");

    private Mass mass;
    private Inertia inertia;
    private Sphere sphere;
    private Mesh mesh;
    private String meshPath;

    public USDRobot(Mass mass, Inertia inertia, Sphere sphere, Mesh mesh) {
        this.mass = mass;
        this.inertia = inertia;
        this.sphere = sphere;
        this.mesh = mesh;
        Double[] inertiaCoordinatesArray = new Double[]{inertia.getIxx(), inertia.getIxy(),
                inertia.getIxz(), inertia.getIyy(), inertia.getIyz(), inertia.getIzz()};
    }

    public USDRobot(){};
    protected String stage;
    protected String physicsScene;
    protected String meshAttribute;
    protected String joint;
    protected String parentJoint;
    protected String sphereAttribute;
    //Prim is rigid body ???
    protected String defaultPrim;
    protected String childPrim;
    protected String lowerLimit;
    protected String upperLimit;
    protected String limit;

    public String importSetUp(){
        return "from pxr import Usd, Sdf, UsdGeom, UsdPhysics, Gf\n";
    }
    public String importOmni(){ return
            "import omni\n";}
    public String pyPhysxImport(){
        return  "from pyphysx import * \n";
    }

    public void setDefaultPrim(String defaultPrim) {
        this.defaultPrim = defaultPrim;
    }

    public String getDefaultPrim() {
        return "defaultPrim = " + this.stage + ".GetPrimAtPath('/" + this.defaultPrim + "')\n" + this.stage + ".SetDefaultPrim(defaultPrim)\n";
    }

    public void setChildPrim(String childPrim) {
        this.childPrim = childPrim;
    }

    public String getChildPrim() {
        return "childPrim = " + this.stage + ".DefinePrim(\"/" + this.defaultPrim + "/" + this.childPrim + "\")\n";
    }

    protected String inertiaAttribute;
    protected String massAttribute;

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStage() {
        return "stage = Usd.Stage.CreateNew(\"" + this.stage + ".usda\")\n";
    }

    public void setPhysicsScene(String physicsScene) {
         this.physicsScene = physicsScene;
    }

    public String getPhysicsScene() {
        return "UsdPhysics.Scene.Define(" + this.stage + ", Sdf.Path(\"/PhysicsScene\"))\n";
    }

    public void setMesh(String meshAttribute) {
        this.meshAttribute = meshAttribute;
    }

    public String getMesh() {
        return "mesh = UsdGeom.Mesh.Define(" + this.stage + ", \"" + this.meshPath + "\")\n";
    }

    public void setJoint(String joint) {
        this.joint = joint;
    }

    public String getJoint() {
        return "joint = UsdPhysics.PrismaticJoint.Define(" + this.stage + ", \"/" + this.parentJoint + "/" + this.joint + "\")\n" + "joint.CreateAxisAttr(\"XYZ\")\n"
                + "joint.CreateLocalPos0Attr().Set(Gf.Vec3f(0.0, 0.0, 0.0))\n";
    }

    public void setSphereAttribute(String sphereAttribute) {
        this.sphereAttribute = sphereAttribute;
    }

    public String setSphereAttribute() {
        return "sphere = " + this.stage + ".DefinePrim(\"/" + this.sphereAttribute + "\", \"Sphere\")\n";
    }

    public String getInertiaAttribute() {
        return inertiaAttribute;
    }

    public void setInertiaAttribute(String inertiaAttribute) {
        this.inertiaAttribute = inertiaAttribute;
    }

    public void setMassAttribute(String massAttribute) {
        this.massAttribute = massAttribute;
    }

    public String getMassAttribute() {
        return "mass = UsdPhysics.MassAPI.Apply(" + this.sphereAttribute +")\n"
               + "mass.CreateMassAttr((" + this.mass.getValue() + "))\n";
    }

    public String saveStage(){
        return this.stage + ".Save()";
    }

    public String getParentJoint() {
        return parentJoint;
    }

    public void setParentJoint(String parentJoint) {
        this.parentJoint = parentJoint;
    }

    public String getLimit() {
        return "";
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }
}