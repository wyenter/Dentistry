function login(){
    var username = document.getElementById("username").value;
    var password = document.getElementById("pass").value;

    var home = document.getElementById("home");
    var div = document.createElement("div");
    div.setAttribute("id", "user");
    var text = document.createTextNode(username);

    //patient needs to see appointments
    if(username=="janedoe1" && password=="1"){
        alert("Welcome Patient");
        document.getElementById("c").style.display="none";
        clearHome();
        document.getElementById("home").style.display="inline";
        home.appendChild(div);
        div.appendChild(text);
        addLogoutButton();
        viewAppointments(username);
    }
    //admin needs to see calendar
    else if(username=="admin" && password=="1"){
        alert("Admin Authentication Successful");
        document.getElementById("c").style.display="none";
        clearHome();
        document.getElementById("home").style.display="inline";
        home.appendChild(div);
        div.appendChild(text);
        addLogoutButton();
        makeCalendar();
    }
    //doctor needs to see schedule
    else if(username=="thedoc" && password=="1"){
        alert("Welcome Doctor");
        document.getElementById("c").style.display="none";
        clearHome();
        document.getElementById("home").style.display="inline";
        home.appendChild(div);
        div.appendChild(text);
        addLogoutButton();
        viewSchedule(username);
    }
    else{
        alert("Invalid Username or Password");
    }
}

function viewAppointments(username){
    var div = document.createElement("div");
    div.setAttribute("id", "appt");
    var div2 = document.createElement("div");
    var txt = document.createTextNode("Your scheduled appointment(s): ");
    var appt = document.createTextNode(getAppts(username));
    home.appendChild(div);
    div.appendChild(txt);
    div.appendChild(div2);
    div.appendChild(appt);
}

//connect to database to get user's appointments. Possibly return as an array.
function getAppts(username){
    return "You have no appointments scheduled.";
}

function makeCalendar(){
    //Calendar.refreshCalendar();
    home.appendChild(document.createTextNode("Calendar"));
}

function viewSchedule(username){
    var div3 = document.createElement("div");
    div3.setAttribute("id", "work");
    var div4 = document.createElement("div");
    var text = document.createTextNode("Your current week at work: ");
    var work = document.createTextNode(getWorkSchedule(username));
    home.appendChild(div3);
    div3.appendChild(text);
    div3.appendChild(div4);
    div3.appendChild(work);
}

function getWorkSchedule(username){
    return "Mon-Fri 8:00 A.M. to 4:00 P.M.";
}

function addLogoutButton(){
    var home = document.getElementById("home");
    var btn = document.createElement("BUTTON");
    var text = document.createTextNode("logout");
    btn.setAttribute("id", "logButton");
    btn.setAttribute("onclick", "logout();");
    home.appendChild(btn);
    btn.appendChild(text);
}

function logout(){
    var home = document.getElementById("home");
    home.style.display="none";
    document.getElementById("c").style.display="inline";
}

function clearHome(){
    var home = document.getElementById("home");
    var child = home.firstChild;
    while(child){
        home.removeChild(child);
        child=home.firstChild;
    }
}