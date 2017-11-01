'use strict';

// Controlador Para Iniciar Sesion
angular.module('Authentication')
.controller('usercontroller',
    ['$scope', '$rootScope', '$location', 'AuthenticationService',
    function ($scope, $rootScope, $location, AuthenticationService) {
        // reset login status
        AuthenticationService.ClearCredentials();

        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.username, $scope.password, function (response) {
				if (response.result) {
                    AuthenticationService.SetCredentials($scope.username, $scope.password);
                    $location.path('/');
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        };
    }]);

// Controlador Recuperar Contraseña
angular.module('Authentication')
.controller("recoveryUserController", 
	function($scope, $http){
	$scope.message="";
	$scope.messageError="";
	$scope.loading = false;	
	
	$scope.SendPost = function () {
		  $scope.loading = true;
		  $scope.inactive= true;
		  $scope.message="";
		  $scope.messageError="";
		  $http.post("http://laptop-diego:9092/api/auth/solicitarCambioPasswd", {
                   	e_mail: $scope.NewPost.correo_e					
                }).then(function successCallBack(data, status, headers, config) {
						   if(data.data.result == true){
							   $scope.message=data.data.message;
						   }else{
							   $scope.messageError=data.data.message;
						   }
						   console.log(data);
						   $scope.loading = false;
						   $scope.inactive= false;
                 }, function errorCallback(data){
                     $scope.messageError=data.data.message;
					 console.log(data);
					 $scope.loading = false;
					 $scope.inactive= false;
				});
			};
 });
 
 
// Controlador Crear Usuarios
angular.module('Authentication')
.controller("usersController", 
	function($scope, $http){
	 $scope.getdata = [];
     $scope.NewPost = {};
	 $scope.message;
	 $scope.loading = false;			
	
	 $scope.limpiar = function () {
		$scope.getdata.push($scope.NewPost);
        $scope.NewPost = {};
	 };
	
	$scope.AddPost = function () {
		 $scope.loading = true;
		 $scope.inactive= true;
		 $scope.message="";
		 $scope.messageError="";
	if($scope.NewPost.password != $scope.NewPost.Confirm_password){
		 $scope.messageError= "Las contraseñas no concuerdan, Por favor valide la información."; 
		 $scope.loading = false;
		 $scope.inactive= false;
	 }else{
		 
		 $http.post("http://laptop-diego:9092/api/clientes", {
                    apellidos: $scope.NewPost.apellidos,
                    documento: $scope.NewPost.documento,
					nombres: $scope.NewPost.nombres,
					telefono: $scope.NewPost.telefono,
					correo_e: $scope.NewPost.correo_e,
					password: $scope.NewPost.password
                }).then(function successCallBack(data, status, headers, config) {
                            if(data.data.success == true){
							   $scope.message=data.data.mensaje;
							   $scope.getdata.push($scope.NewPost);
							   $scope.NewPost = {};
							}else{
							   $scope.messageError=data.data.mensaje;
						   }
						   console.log(data);
						   $scope.loading = false;
						   $scope.inactive= false;
                 }, function errorCallback(data){
                     $scope.messageError=data.data.mensaje;
					 console.log(data);
					 $scope.loading = false;
					 $scope.inactive= false;
                 });
		}
	};
 });
 
 
// Controlador Para Iniciar Sesion
angular.module('Authentication')
.controller('restoreAccountController',
    ['$scope', '$location', '$http',
    function ($scope, $location, $http) {
        
        $scope.restore = function () {
            $scope.dataLoading = true;
			
			var objsURL = $location.search();
			var tokenURL = objsURL.token;
			
		if($scope.clave != $scope.Confirmclave){
			$scope.error = "Las contraseñas no coinciden."; 
			$scope.dataLoading = false;
		}else{
			$http.post("http://laptop-diego:9092/api/auth/procesarCambioPasswd", {
                   	token: tokenURL,
					passwd: $scope.clave			
                }).then(function successCallBack(data, status, headers, config) {
						   if(data.data.result == true){
							   $scope.success = data.data.message;
						   }else{
							   $scope.error = data.data.message;
						   }
						   console.log(data);
						   $scope.dataLoading  = false;
						   
                 }, function errorCallback(data){
                     $scope.error = data.data.message;
					 console.log(data);
					 $scope.dataLoading  = false;
				});
			}
		//console.log("clave:"+$scope.clave+" - Confirmclave:"+$scope.Confirmclave+" token="+tokenURL);
        };
    }]);