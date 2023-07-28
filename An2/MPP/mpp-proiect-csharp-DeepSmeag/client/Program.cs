
using mpp_proiect_csharp_DeepSmeag.uiForms;


namespace mpp_proiect_csharp_DeepSmeag
{
    internal class Program
    {
        [STAThread]
        static void Main()
        {
            log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
            log.Info("Application started");
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginMenu());

        }
    }
}