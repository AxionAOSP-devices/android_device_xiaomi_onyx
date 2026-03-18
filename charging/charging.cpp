#include <android/log.h>
#include <chrono>
#include <fstream>
#include <string>
#include <thread>

using namespace std;

int main() {
    const string s_p = "/sys/class/power_supply/battery/status";
    const string t_p = "/sys/class/power_supply/battery/temp";
    const string c_p = "/sys/class/power_supply/battery/constant_charge_current";
    const string p_p = "/sys/class/power_supply/battery/capacity";

    int last = -1;

    while (true) {
        string stat;
        ifstream s_f(s_p);
        if (!(s_f >> stat)) {
            this_thread::sleep_for(chrono::seconds(2));
            continue;
        }

        if (stat != "Charging") {
            if (last != -1) {
                ofstream(c_p) << 5000000;
                last = -1;
            }
            this_thread::sleep_for(chrono::seconds(10));
            continue;
        }

        int temp = 0, cap = 0;
        ifstream t_f(t_p);
        ifstream p_f(p_p);

        if (!(t_f >> temp) || !(p_f >> cap)) {
            this_thread::sleep_for(chrono::seconds(2));
            continue;
        }

        int target = (temp >= 44000 || cap >= 90) ? 1000000 : 5000000;

        if (target != last) {
            ofstream f(c_p);
            if (f.is_open()) {
                f << target;
                __android_log_print(ANDROID_LOG_INFO, "thermal_batt_manager",
                    "Temp: %d, Cap: %d, Limit: %d", temp, cap, target);
                last = target;
            }
        }
        this_thread::sleep_for(chrono::seconds(5));
    }
    return 0;
}
