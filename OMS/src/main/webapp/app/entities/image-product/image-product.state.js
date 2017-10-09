(function () {
    'use strict';

    angular
            .module('omsApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('image-product', {
                    parent: 'entity',
                    url: '/image-product',
                    data: {
                        authorities: ['ROLE_USER'],
                        pageTitle: 'omsApp.producto.home.title'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/image-product/image-product.html',
                            controller: 'ImageProductController',
                            controllerAs: 'vm'
                        }
                    }
                });
    }

})();
