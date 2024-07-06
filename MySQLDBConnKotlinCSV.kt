import java.sql.*
import java.io.File
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
    val resultSet = statement.executeQuery("SELECT * FROM employee_master") // Replace with your table name
    val fileName = "/home/subhroneel/Documents/emp_mast1.csv"
    val file = File(fileName)
    var fileContent = ""
    val metaData = resultSet.metaData
    val columnCount = metaData.columnCount
    for (i in 1 .. columnCount) {
        fileContent+=metaData.getColumnName(i).uppercase()
        if(i<columnCount)
            fileContent+="~"
    }
    fileContent+="\n"
    while (resultSet.next()) {
        // Process the result set
//        println(resultSet.getString("emp_code") + " - " + resultSet.getString("emp_name")) // Replace with your column name
        for (i in 1 .. columnCount) {
            fileContent += resultSet.getString(i)
            if (i < columnCount)
                fileContent += "~"
        }
        fileContent+="\n"
    }
    file.writeText(fileContent)
    // 4. Close the connection
    resultSet.close()
    statement.close()
    connection.close()
}