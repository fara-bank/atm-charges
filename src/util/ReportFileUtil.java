package util;

import dao.ReportDao;
import model.AcqReport;

import java.io.*;

public class ReportFileUtil {

    final ReportDao dao = new ReportDao();
    final String baseFolder = "F:\\Farabank";

    public void saveAllRow() {
        getAllReports(new File(baseFolder));
    }

    private void getAllReports(File folder) {
        File[] listOfFiles = folder.listFiles();

        for (File item : listOfFiles) {
            if (item.isFile()) {

                if (item.getName().contains(".acq")) {
                    System.out.println("File " + item.getName());
                    readAllLines(item);
                }

            } else if (item.isDirectory()) {
                getAllReports(item);
            }
        }
    }

    private void readAllLines(File file) {
        BufferedReader br = null;
        try {
            // Open the file
            FileInputStream fstream = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fstream));

            dao.begin();

            String strLine;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {

                // Print the content on the console
                System.out.println(strLine);

                AcqReport report = valueOf(strLine, file.getName().substring(file.getName().indexOf('.')));
                dao.save(report);
            }

            dao.commit();

        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            dao.rollback();
        } finally {
            //Close the input stream
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (dao != null)
                dao.close();
        }
    }

    private AcqReport valueOf(String strLine, String filename) {
        String[] fields = strLine.split("\\|");

        AcqReport report = new AcqReport();

        report.setRowNo(fields[0]);
        report.setProcessType(fields[1]);
        report.setTransactionDate(fields[2]);
        report.setTransactionTime(fields[3]);
        report.setStan(fields[4]);
        report.setCardNumber(fields[5]);
        report.setIssuerCode(fields[6]);
        report.setTerminalNo(fields[7]);
        report.setAmount(fields[8]);
        report.setDeviceType(fields[9]);
        report.setCharge(fields[10]);
        report.setExteraInfo1(fields[11]);
        report.setFunctionCode(fields[12]);
        report.setLedgerDate(fields[13]);
        report.setLedgerNoShetab(fields[14]);
        report.setIssuerCcyAmount(fields[15]);
        report.setIssuerCcy(fields[16]);
        report.setExtraInfo2(fields[17]);
        report.setExtraInfo3(fields[18]);
        report.setExtraInfo4(fields[19]);
        report.setExtraInfo5(fields[20]);
        report.setExtraInfo6(fields[21]);
        report.setExtraInfo7(fields[22]);
        report.setExtraInfo8(fields[23]);
        report.setFileType(filename);
        report.setUserId("SYS");

        return report;
    }
}
