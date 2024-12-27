<%-- 
    Document   : output
    Created on : Feb 1, 2011, 3:21:13 PM
    Author     : trairak
--%>

<%@page import="java.util.TreeSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/includes/header.html"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/includes/title.html"%>
<%
    String outputFileName = (String) request.getAttribute("outputFileName");
    String outputSubFolder = (String) request.getAttribute("outputSubFolder");
    TreeSet<String> graphSet = (TreeSet<String>) request.getAttribute("graphSet");
    int totalclusters = graphSet.size();
    System.out.println(outputFileName);
%>
<p class="left">Total number of clusters = <%= totalclusters%></p>
<p class="left"><a href="outputFolder/<%= outputFileName%>">Download data</a> (Tab-delimited text file)</p>
<p class="left">To save data - right click on the link and select "Save Link As..."</p>
<hr>
<table style="margin-left: 50px; border: 1px solid black">
    <% for (String string : graphSet) {
            String alt = string.split("\\.")[0];
    %>
    <tr>
        <td style="border: 1px solid black;">
            <img src="outputFolder/<%= outputSubFolder%>/<%= string%>" alt="<%= alt%>">
        </td>
    </tr>
    <% }%>
</table>
<%@include file="/includes/footer.html"%>
