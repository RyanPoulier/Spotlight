/**
 * Created by Padmaka on 8/14/16.
 */

function getComplaints() {
    $.ajax({
        url: "http://" + host + ":" + port + "/spotlight-api/issues",
        type: "GET",
        contentType: "application/json",
        success: function (result) {
            console.log(result);
            displaycomplaints(result);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.error("Failed to get issues");
        }
    });
}


function displaycomplaints(result) {

    $(".main").html("<h2>Spotlight Admin Portal - Complaints</h2><br><br><br>");

    for (i = 0; i < result.length; i++) {
        $(".main").append('<div class="container-fluid issue-container" style="background: aliceblue; border-radius: 10px;">'
            + '<table class="table" style="margin: 10px;">'
            + '<tbody>'
            + '<tr>'
            + '<td><h5>' + result[i].title + ' @ ' + result[i].latitude + ', ' + result[i].longitude + '</h5></td>'
            +  '</tr>'
            +  '<tr>'
            + '<td>Submission Timestamp: </td>'
            +  '</tr>'
            +  '<tr>'
            + '<td>' + result[i].address + '</td>'
            + '</tr>'
            + '</tbody>'
            + '</table>'
            + '</div><br>');
    }
}