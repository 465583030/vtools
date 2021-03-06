package com.omarea.shell.cpucontrol;

import android.content.Context;
import android.widget.Toast;

import com.omarea.shell.SysUtils;

import java.io.File;
import java.util.ArrayList;

public class CpuFrequencyUtils {
    public static String[] getAvailableFrequencies(Integer cluster) {
        if (cluster >= getClusterInfo().size()) {
            return new String[]{  };
        }
        String cpu = "cpu" + getClusterInfo().get(cluster)[0];
        String[] frequencies;
        if (new File(Constants.scaling_available_freq.replace("cpu0", cpu)).exists()) {
            frequencies = SysUtils.readOutputFromFile(Constants.scaling_available_freq.replace("cpu0", cpu)).split(" ");
            return frequencies;
        } else {
            return new String[]{};
        }
    }
    public static String getCurrentMaxFrequency(Integer cluster) {
        if (cluster >= getClusterInfo().size()) {
            return "";
        }
        String cpu = "cpu" + getClusterInfo().get(cluster)[0];
        return SysUtils.readOutputFromFile(Constants.scaling_max_freq.replace("cpu0", cpu));
    }
    public static String getCurrentMinFrequency(Integer cluster) {
        if (cluster >= getClusterInfo().size()) {
            return "";
        }
        String cpu = "cpu" + getClusterInfo().get(cluster)[0];
        return SysUtils.readOutputFromFile(Constants.scaling_min_freq.replace("cpu0", cpu));
    }
    public static String[] getAvailableGovernors(Integer cluster) {
        if (cluster >= getClusterInfo().size()) {
            return new String[]{  };
        }
        String cpu = "cpu" + getClusterInfo().get(cluster)[0];
        return SysUtils.readOutputFromFile(Constants.scaling_available_governors.replace("cpu0", cpu)).split(" ");
    }
    public static String getCurrentScalingGovernor(Integer cluster) {
        if (cluster >= getClusterInfo().size()) {
            return "";
        }
        String cpu = "cpu" + getClusterInfo().get(cluster)[0];
        return SysUtils.readOutputFromFile(Constants.scaling_governor.replace("cpu0", cpu));
    }

    public static void setMinFrequency(String minFrequency, Integer cluster, Context context) {
        if (cluster >= getClusterInfo().size()) {
            return;
        }

        String[] cores = getClusterInfo().get(cluster);
        ArrayList<String> commands = new ArrayList<>();
        /*
         * prepare commands for each core
		 */
        if (minFrequency != null) {
            for (String core : cores) {
                commands.add("chmod 0664 " + Constants.scaling_min_freq.replace("cpu0", "cpu" + core));
                commands.add("echo " + minFrequency + " > " + Constants.scaling_min_freq.replace("cpu0", "cpu" + core));
            }

            boolean success = SysUtils.executeRootCommand(commands);
            if (success) {
                Toast.makeText(context, "OK!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void setMaxFrequency(String maxFrequency, Integer cluster, Context context) {
        if (cluster >= getClusterInfo().size()) {
            return;
        }

        String[] cores = getClusterInfo().get(cluster);
        ArrayList<String> commands = new ArrayList<>();
        /*
         * prepare commands for each core
		 */
        if (maxFrequency != null) {
            commands.add("chmod 0664 /sys/module/msm_performance/parameters/cpu_max_freq");
            for (String core : cores) {
                commands.add("chmod 0664 " + Constants.scaling_max_freq.replace("cpu0", "cpu" + core));
                commands.add("echo " + maxFrequency + " > " + Constants.scaling_max_freq.replace("cpu0", "cpu" + core));
                commands.add("echo " + core + ":" + maxFrequency +"> /sys/module/msm_performance/parameters/cpu_max_freq");
            }

            boolean success = SysUtils.executeRootCommand(commands);
            if (success) {
                Toast.makeText(context, "OK!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void setGovernor(String governor, Integer cluster, Context context) {
        if (cluster >= getClusterInfo().size()) {
            return;
        }

        String[] cores = getClusterInfo().get(cluster);
        ArrayList<String> commands = new ArrayList<>();
        /*
         * prepare commands for each core
		 */
        if (governor != null) {
            for (String core : cores) {
                commands.add("chmod 0644 " + Constants.scaling_governor.replace("cpu0", "cpu" + core));
                commands.add("echo " + governor + " > " + Constants.scaling_governor.replace("cpu0", "cpu" + core));
            }

            boolean success = SysUtils.executeRootCommand(commands);
            if (success) {
                Toast.makeText(context, "OK!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String getInputBoosterFreq() {
        return SysUtils.readOutputFromFile("/sys/module/cpu_boost/parameters/input_boost_freq");
    }

    public static String getInputBoosterTime() {
        return SysUtils.readOutputFromFile("/sys/module/cpu_boost/parameters/input_boost_ms");
    }

    public static void setInputBoosterTime(String time) {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("chmod 0644 /sys/module/cpu_boost/parameters/input_boost_ms");
        commands.add("echo "+ time +" > /sys/module/cpu_boost/parameters/input_boost_ms");

        SysUtils.executeRootCommand(commands);
    }

    public static boolean getCoreOnlineStae(int coreIndex) {
        return SysUtils.readOutputFromFile("/sys/devices/system/cpu/cpu0/online".replace("cpu0", "cpu" + coreIndex)).equals("1");
    }

    public static void setCoreOnlineState(int coreIndex, boolean online) {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("chmod 0644 /sys/devices/system/cpu/cpu0/online".replace("cpu0", "cpu" + coreIndex));
        commands.add("echo "+ (online ? "1" : "0") +" > /sys/devices/system/cpu/cpu0/online".replace("cpu0", "cpu" + coreIndex));

        SysUtils.executeRootCommand(commands);
    }

    public static void setInputBoosterFreq(String freqs) {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("chmod 0644 /sys/module/cpu_boost/parameters/input_boost_freq");
        commands.add("echo "+ freqs +" > /sys/module/cpu_boost/parameters/input_boost_freq");

        SysUtils.executeRootCommand(commands);
    }

    public static int getCoreCount() {
        int cores = 0;
        while (true) {
            File file = new File(Constants.cpu_dir.replace("cpu0", "cpu" + cores));
            if (file.exists()) {
                cores++;
            } else {
                return cores;
            }
        }
    }

    private static ArrayList<String[]> cpuClusterInfo;
    public static ArrayList<String[]> getClusterInfo() {
        if (cpuClusterInfo != null) {
            return cpuClusterInfo;
        }

        int cores = 0;
        cpuClusterInfo = new ArrayList<>();
        ArrayList<String> clusters = new ArrayList<>();
        while (true) {
            File file = new File("/sys/devices/system/cpu/cpu0/cpufreq/related_cpus".replace("cpu0", "cpu" + cores));
            if (file.exists()) {
                String  relatedCpus = SysUtils.executeCommandWithOutput(false, "cat /sys/devices/system/cpu/cpu0/cpufreq/related_cpus".replace("cpu0", "cpu" + cores)).trim();
                if (!clusters.contains(relatedCpus) && !relatedCpus.isEmpty()) {
                    clusters.add(relatedCpus);
                }
            } else {
                break;
            }
            cores ++;
        }
        for (int i=0;i<clusters.size();i++) {
            cpuClusterInfo.add(clusters.get(i).split(" "));
        }

        return cpuClusterInfo;
    }

    public static String getSechedBoostState() {
        return SysUtils.readOutputFromFile(Constants.sched_boost);
    }

    public static void setSechedBoostState(boolean enabled ,Context context) {
        String val = enabled ? "1" : "0";
        ArrayList<String> commands = new ArrayList<>();
        commands.add("chmod 0664 " + Constants.sched_boost);
        commands.add("echo " + val + " > " + Constants.sched_boost);

        boolean success = SysUtils.executeRootCommand(commands);
        if (success) {
            Toast.makeText(context, "OK!", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getParametersCpuMaxFreq() {
        return SysUtils.readOutputFromFile("/sys/module/msm_performance/parameters/cpu_max_freq");
    }

    public static void setParametersCpuMaxFreq() {

    }

    public static String[] toMhz(String... values) {
        String[] frequency = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            try {
                frequency[i] = (Integer.parseInt(values[i].trim()) / 1000) + " Mhz";
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        return frequency;
    }
}
