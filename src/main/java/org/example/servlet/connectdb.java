package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/display")
public class connectdb extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jdbc_demo",
                    "root",
                    "akhila"
            );

            // Create statement
            Statement st = con.createStatement();

            // Execute SQL query
            ResultSet rs = st.executeQuery("SELECT * FROM student");

            // HTML page
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Student Records</title>");
            out.println("</head>");

            out.println("<body>");
            out.println("<h1>Student Records</h1>");

            out.println("<table border='1'>");

            // Table headings
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Branch</th>");
            out.println("<th>Marks</th>");
            out.println("</tr>");

            // Display database records
            while (rs.next()) {

                out.println("<tr>");

                out.println("<td>" + rs.getInt("id") + "</td>");

                out.println("<td>" + rs.getString("name") + "</td>");

                out.println("<td>" + rs.getString("branch") + "</td>");

                out.println("<td>" + rs.getInt("marks") + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

            // Close resources
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}