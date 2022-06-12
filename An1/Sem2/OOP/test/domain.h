#include <string>
using namespace std;
class Device
{

private:
    string tip, model, an, culoare, pret;

public:
    Device(string tip, string model, string an, string culoare, string pret) : tip{tip}, model{model}, an{an}, culoare{culoare}, pret{pret} {};
    string getTip();
    string getModel();
    string getAn();
    string getCuloare();
    string getPret();
};