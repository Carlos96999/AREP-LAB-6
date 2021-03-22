var apiClient = (function () {
    var url="https://localhost:5000";

    function info() {

        axios.get(url+"/servicio").then(res=>{
            console.log(url+"/servicio")
            $("#servicio").text(res.data);
        })
    }
    function login(){
        var user={email:document.getElementById("email").value,password:document.getElementById("password").value}
        axios.post(url+"/login",user).then(res=>{
            //window.location.href="authorized.html";
			window.location.href="b.html";
        })
    }

    return {
        login:login,
        info:info
    };
})();