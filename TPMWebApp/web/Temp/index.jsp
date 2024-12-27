<%-- 
    Document   : index
    Created on : Feb 1, 2011, 3:08:58 PM
    Author     : trairak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/includes/header.html"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/includes/title.html"%>
<script type="text/javascript">
    function validate(form) {
        if (form.input.value == "") {
            alert("Please choose a file");
            form.input.focus();
        } else {
            form.submit();
        }
    }
</script>

<%
    // get attributes from the request
    String message = (String) request.getAttribute("message");
    if (message == null) {
        message = "";
    }
%>

<table id="main" style="table-layout:fixed">
    <col style="width: 40%">
    <col style="width: 5%">
    <col style="width: 55%">
    <tr>
        <td>
            <p><strong>TPM algorithm</strong> clusters any time-series data set, specifically iTRAQ LC-MS/MS data sets.  The data points that have a similar behavior over the time course are clustered together.</p>
            <form action="TPMServlet" enctype="multipart/form-data" target="_blank" method="post" style="background-color: paleturquoise">
                <table style="border-spacing: 5px; padding: 5px">
                    <tr>
                        <td>
                            <label for="input">Please select a tab-delimited input text file:<br><span style="color: blue">(see input format below)</span></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="file" name="input" id="input">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" value="Submit" onClick="validate(this.form)">
                            <p style="color: red"><%= message%></p>
                        </td>
                    </tr>
                </table>
            </form>
            <p><strong>TPM algorithm</strong> was developed by Fahad Saeed at the <a href="https://intramural.nhlbi.nih.gov/labs/LKEM_G/LKEM/Pages/default.aspx" target="_blank">Epithelial Systems Biology Laboratory</a>, National Heart, Lung, and Blood Institute in Bethesda, Maryland USA.</p>
            <p>If you use this algorithm please cite the following paper:</p>
            <p>Fahad Saeed, Trairak Pisitkun, Mark Knepper and Jason D Hoffert, <strong>"Mining Temporal Patterns from iTRAQ Mass Spectrometry (LC-MS/MS) Data"</strong>, The Proceedings of the ISCA 3rd International Conference on Bioinformatics and Computational Biology (BiCoB), Vol 1. pp 152-159 New Orleans, Louisiana, USA, March 23-25, 2011 (ISBN: 978-1-880843-81-9)</p>
            <p>Link to full paper: <a href="http://arxiv.org/abs/1104.5510v1" target="_blank">arXiv:1104.5510v1</a></p>
            <p>If you have any question or want to report a bug about the algorithm please <a href="https://intramural.nhlbi.nih.gov/labs/LKEM_G/LKEM/Pages/default.aspx" target="_blank">contact us</a>.</p>
        </td>
        <td>

        </td>
        <td>
            <img src="images/TPM.png" alt="TPM image">
        </td>
    </tr>
</table>
<hr>
<p class="left" style="color: blue; font-weight: bold">Input format</p>
<p class="left">
    Data Input: The data is assumed to be the following. The first column is considered the peptide sequence or any other nomenclature that you are analyzing.<br>
    The columns that follow are the numbers that represent the entity that you wish to cluster for each time point (t1, t2, … etc).
</p>
<p class="left">
    For example:
</p>
<table style="margin-left: 50px; border-spacing: 10px">
    <tr>
        <td>peptide_1</td><td>0.223</td><td>0.440</td><td>0.232</td><td>0.232</td>
    </tr>
    <tr>
        <td>peptide_2</td><td>0.232</td><td>0.870</td><td>0.670</td><td>0.230</td>
    </tr>
    <tr>
        <td>peptide_3</td><td>0.670</td><td>0.440</td><td>-0.340</td><td>-0.220</td>
    </tr>
    <tr>
        <td>peptide_4</td><td>0.232</td><td>0.212</td><td>0.909</td><td>0.313</td>
    </tr>
    <tr>
        <td>peptide_5</td><td>0.771</td><td>0.111</td><td>-0.313</td><td>0.111</td>
    </tr>
</table>
<p class="left">
    The example above shows the <strong>tab-delimited text file</strong> with first column representing peptide names.<br>
    The columns in the next column represents the number for the first time point, the second column for second time point and so on.<br>
    Please make sure that you don't have spaces in the start and end of your text file.
</p>
<%@include file="/includes/footer.html"%>
