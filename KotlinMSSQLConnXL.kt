import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.sql.*
import java.io.FileOutputStream
fun main() {
    Class.forName("net.sourceforge.jtds.jdbc.Driver")
    val connection = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.29.234:1433;" +
            "encrypr=true;databaseName=greenply_23;user=sa;password=sa")
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("select * from employee_salary")
    val workbook = XSSFWorkbook()
    val worksheet = workbook.createSheet("emp_sal")
    val metaData = resultSet.metaData
    val columnCount = metaData.columnCount
    var r = 0
    var row = worksheet.createRow(r)
    for(i in 1..columnCount)
    {
        val cell = row.createCell(i-1)
        cell.setCellValue(metaData.getColumnName(i).uppercase())
    }
    while(resultSet.next())
    {
        r+=1
        row = worksheet.createRow(r)
        for(i in 1..columnCount)
        {
            val cell = row.createCell(i-1)
            cell.setCellValue(resultSet.getString(i))
        }
    }
    FileOutputStream("/home/subhroneel/Downloads/emp_sal1.xlsx")
        .use { fileout -> workbook.write(fileout)}
    workbook.close()
    statement.close()
    connection.close()
}