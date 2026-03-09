public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        InputConnectable pj = reg.getFirstOfType(InputConnectable.class);
        // Cast to Powerable to call powerOn if needed
        if (pj instanceof Powerable) {
            ((Powerable)pj).powerOn();
        }
        pj.connectInput("HDMI-1");

        BrightnessControllable lights = reg.getFirstOfType(BrightnessControllable.class);
        lights.setBrightness(60);

        TemperatureControllable ac = reg.getFirstOfType(TemperatureControllable.class);
        ac.setTemperatureC(24);

        AttendanceProvider scan = reg.getFirstOfType(AttendanceProvider.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        Powerable pj = (Powerable) reg.getFirstOfType("Projector");
        Powerable lights = (Powerable) reg.getFirstOfType("LightsPanel");
        Powerable ac = (Powerable) reg.getFirstOfType("AirConditioner");
        pj.powerOff();
        lights.powerOff();
        ac.powerOff();
    }
}
