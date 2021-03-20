var apiClient = (function () {
    var url="localhost:5000";

    function info() 
	{
        axios.get(url+"/acceso").then(res=>{
            console.log(url+"/acceso")
            $("#acceso").text(res.data);
        })
    }
    function login(){
        var user={email:document.getElementById("correo").value,password:document.getElementById("contrasena").value}
        axios.post(url+"/login",user).then(res=>{
			if(res.data != "")
			{
				alert(res.data)
			}
			else
			{
				window.location.href="accesoAutorizado.html";
			}
        })
    }

    return {
        login:login,
        info:info
    };
})();