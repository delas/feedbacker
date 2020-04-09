var path = $(location).attr('href');
var assignmentUid = path.substring(path.indexOf("$") - 3, path.indexOf("?"));
$.get("https://dtu.onlineeksamen.dk/api/AssignmentAdministration/GetHandIns/" + assignmentUid, {"headers":{"x-currentusertoken":userToken }}, function(data){ $("#container").prepend("<textarea style='position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 10000'>" + JSON.stringify(data) + "</textarea>"); });
