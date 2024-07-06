import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFCell
import java.sql.*
import java.io.FileOutputStream

fun main() {
    // 1. Load the JDBC driver
    Class.forName("com.mysql.cj.jdbc.Driver")

    // 2. Establish the connection
    val connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/greendb", // Replace with your details
        "neel",
        "P@ssw0rd"
    )

    // 3. Execute a simple query (optional)
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("select * from employee_master") // Replace with your table name
    val workbook = XSSFWorkbook()
    val workSheet = workbook.createSheet("emp_mast")
    val metaData = resultSet.metaData
    val columnCount = metaData.columnCount
    var row = workSheet.createRow(0)
    for (i in 1 .. columnCount) {
        val cell = row.createCell(i-1)
        cell.setCellValue(metaData.getColumnName(i).uppercase())
    }
    var cell:XSSFCell
    var r = 1
    while(resultSet.next())
    {
        row = workSheet.createRow(r)
        for (i in 1 .. columnCount) {
            cell = row.createCell(i-1)
            cell.setCellValue(resultSet.getString(i))
        }
        r+=1
    }
    // Write the workbook to a file
    FileOutputStream("/home/subhroneel/Documents/greendb_new1.xlsx").use { fileOut ->
        workbook.write(fileOut)
    }
    workbook.close()
    // 4. Close the connection
    resultSet.close()
    statement.close()
    connection.close()
}