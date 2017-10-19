(function () {
    'use strict';

    angular
            .module('omsApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('image-product-small', {
                    parent: 'entity',
                    url: '/image-product/small/{id}',
                    data: {
                        authorities: ['ROLE_USER'],
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', 'ImageProduct', function ($stateParams, $state, $uibModal, ImageProduct) {
                            ImageProduct.setTamanio('Pequeña'),
                            $uibModal.open({
                                templateUrl: 'app/entities/image-product/image-product.html',
                                controller: 'ImageProductController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            nombre: null,
                                            descripcion: null,
                                            precio: null,
                                            estado: null,
                                            disponibilidad: null,
                                            fechaRevDisponibilidad: null,
                                            marca: null,
                                            keyWords: null,
                                            id: $stateParams.id
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $state.go('producto', null, {reload: 'producto'});
                            }, function () {
                                $state.go('producto');
                            });
                        }]
                })
                .state('image-product-medium', {
                    parent: 'entity',
                    url: '/image-product/medium/{id}',
                    data: {
                        authorities: ['ROLE_USER'],
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', 'ImageProduct', function ($stateParams, $state, $uibModal, ImageProduct) {
                            ImageProduct.setTamanio('Mediana'),
                            $uibModal.open({
                                templateUrl: 'app/entities/image-product/image-product.html',
                                controller: 'ImageProductController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            nombre: null,
                                            descripcion: null,
                                            precio: null,
                                            estado: null,
                                            disponibilidad: null,
                                            fechaRevDisponibilidad: null,
                                            marca: null,
                                            keyWords: null,
                                            id: $stateParams.id
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $state.go('producto', null, {reload: 'producto'});
                            }, function () {
                                $state.go('producto');
                            });
                        }]
                })
                .state('image-product-large', {
                    parent: 'entity',
                    url: '/image-product/large/{id}',
                    data: {
                        authorities: ['ROLE_USER'],
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', 'ImageProduct', function ($stateParams, $state, $uibModal, ImageProduct) {
                            ImageProduct.setTamanio('Grande'),
                            $uibModal.open({
                                templateUrl: 'app/entities/image-product/image-product.html',
                                controller: 'ImageProductController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            nombre: null,
                                            descripcion: null,
                                            precio: null,
                                            estado: null,
                                            disponibilidad: null,
                                            fechaRevDisponibilidad: null,
                                            marca: null,
                                            keyWords: null,
                                            id: $stateParams.id
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $state.go('producto', null, {reload: 'producto'});
                            }, function () {
                                $state.go('producto');
                            });
                        }]
                })
                .state('image-campania', {
                    parent: 'entity',
                    url: '/image-product/campania/{id}',
                    data: {
                        authorities: ['ROLE_USER'],
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', 'ImageProduct', function ($stateParams, $state, $uibModal, ImageProduct) {
                            ImageProduct.setTamanio('Campania'),
                            $uibModal.open({
                                templateUrl: 'app/entities/image-product/image-product.html',
                                controller: 'ImageProductController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            nombre: null,
                                            descripcion: null,
                                            precio: null,
                                            estado: null,
                                            disponibilidad: null,
                                            fechaRevDisponibilidad: null,
                                            marca: null,
                                            keyWords: null,
                                            id: $stateParams.id
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $state.go('campania', null, {reload: 'campania'});
                            }, function () {
                                $state.go('campania');
                            });
                        }]
                });
    }

})();
