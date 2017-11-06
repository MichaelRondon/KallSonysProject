var app = angular.module("myapp",['ui.bootstrap','infinite-scroll']);

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
 
 app.controller('ProductoController', function($scope, Reddit) {
  $scope.reddit = new Reddit();
});

// Reddit constructor function to encapsulate HTTP and pagination logic
app.factory('Reddit', function($http) {
  var Reddit = function() {
    this.items = [];
    this.busy = false;
    this.after = '';
	this.page = 0;
	this.total_pages = 0;
  };

  Reddit.prototype.nextPage = function() {
    if (this.busy) return;
    this.busy = true;

	var url = "http://laptop-michael:7076/api/producto/buscar/scroll?page=" + this.page + '&items_per_page=20';
    //var url = "https://api.reddit.com/hot?after=" + this.after + "&jsonp=JSON_CALLBACK";
    //$http.jsonp(url).success(function(data) {
    $http.get(url).success(function(data) {
      var items = data.productos;
	  this.page = data.page.number + 1;
	  this.total_pages = data.page.total_pages;
      for (var i = 0; i < items.length; i++) {
        this.items.push(items[i]);
      };
      this.after = "t3_" + this.items[this.items.length - 1].id;
      this.busy = false;
    }.bind(this));
  };

  return Reddit;
});
 
 