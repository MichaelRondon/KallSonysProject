var app = angular.module("myapp",['ui.bootstrap']);

 app.controller("headercontroller", function($scope){  
      $scope.countryList = [  
           "Televisor 32 Pulg - Sony", "Televisor 40 Pulg - Sony", "Televisor 50 Pulg - Lg", "Televisor 50 Pulg - Panasonic"  
      ];  
      $scope.complete = function(string){  
           $scope.hidethis = false;  
           var output = [];  
		   if(string != ""){
				angular.forEach($scope.countryList, function(country){  
                if(country.toLowerCase().indexOf(string.toLowerCase()) >= 0)  
                {  
                     output.push(country);  
                }  
             }); 
		   }
            
		   
		   $scope.filterCountry = output;  
      }  
      $scope.fillTextbox = function(string){  
           $scope.country = string;  
           $scope.hidethis = true;  
      }  
 });
 
 app.controller("SliderPage",function($scope, $http){
	$scope.dataLoading = true;
	$scope.myInterval = 3000;
	$scope.urlProductos = "http://localhost/KallsonysMovil/products.html#/ContentProducts?Id=";
	
	var output = [];
	 $http({
                method: 'GET',
                url: 'http://laptop-diego:9091/api/producto/campanias?estado=ACTIVO&categoria=PRINCIPAL'
            }).then(function successCallback(response) {
				angular.forEach(response.data.campanias, function (value, key)
					{
						output.push(
							{
								"image": value.urlImage,
								"productId": value.productos[0].id
							}
						);
					});
					
					$scope.dataLoading = false;
					$scope.slides = output;

		      }, function errorCallback(response) {
				$scope.dataLoading = false;
				console.log(response);
      });
 });
 
  
 
 