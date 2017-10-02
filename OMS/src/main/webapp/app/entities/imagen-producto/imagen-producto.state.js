(function () {
    'use strict';

    angular
            .module('omsApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'paginationConstants'];

    function stateConfig($stateProvider, paginationConstants) {
        $stateProvider
                .state('imagen-producto', {
                    parent: 'producto.edit',
                    url: '/imagen?filesPage&filesSort&filesSearch&filesCount',
                    data: {
                        permissions: ['ROLE_USER'],
                        pageTitle: 'Imágen del producto'
                    },
                    ncyBreadcrumb: {
                        label: 'Imagenes adjuntas'
                    },
                    views: {
                        'dialog-content@': {
                            templateUrl: 'app/entities/_components/entity-list/templates/list-base.html',
                            controller: 'ImagenProductoController',
                            controllerAs: 'vm'
                        }
                    },
                    params: {
                        filesPage: {
                            value: '1',
                            squash: true
                        },
                        filesSort: {
                            value: 'id,asc',
                            squash: true
                        },
                        filesSearch: null,
                        filesCount: {
                            value: paginationConstants.itemsPerPage.toString(),
                            squash: true
                        }
                    },
                    resolve: {
                        pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                                return {
                                    page: PaginationUtil.parsePage($stateParams.filesPage),
                                    sort: $stateParams.filesSort,
                                    predicate: PaginationUtil.parsePredicate($stateParams.filesSort),
                                    ascending: PaginationUtil.parseAscending($stateParams.filesSort),
                                    search: $stateParams.filesSearch,
                                    size: parseInt($stateParams.filesCount)
                                };
                            }],
//                        estadoBaches: ['entity', function (entity) {
//                                return entity;
//                            }],
//                        inspeccionEntity: ['inspeccionEntity', function (inspeccionEntity) {
//                                return inspeccionEntity;
//                            }],
                        archivosListListener: ['EntityListener', function (EntityListener) {
                                return EntityListener.make();
                            }]
                    }
                })
                .state('imagen-producto.delete', {
                    parent: 'imagen-producto',
                    url: '/{id_archivo}/delete',
                    data: {
                        permissions: ['ROLE_USER']

                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', 'archivosListListener', function ($stateParams, $state, $uibModal, archivosListListener) {
                            $uibModal.open({
                                templateUrl: 'app/entities/imagen-producto/imagen-producto-delete-dialog.html',
                                controller: 'ImagenProductoDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Producto', function (ProductoImageService) {
                                            return ProductoImageService.get({id: $stateParams.id});
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^');
                                archivosListListener.dispatch();
                            }, function () {
                                $state.go('^');
                                archivosListListener.dispatch();
                            });
                        }]
                })
                .state('imagen-producto.upload-file', {
                    parent: 'imagen-producto',
                    url: '/upload-file',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', 'archivosListListener', 'EntityFileUploadControllerConfig', function ($stateParams, $state, $uibModal, archivosListListener, EntityFileUploadControllerConfig) {
                            this.modal = $uibModal.open({
                                templateUrl: 'app/entities/_components/entity-file-uploader/templates/modal-file-upload.html',
                                controller: 'EntityFileUploadController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'md',
                                resolve: {
                                    controllerConfig: function () {
                                        return new EntityFileUploadControllerConfig({
                                            resourceURL: 'api/imagen-productos/smallImage',
                                            uploadSuccessEventName: 'omsApp:smallImageUpdate',
                                            fileParamName: 'archivo',
                                            requestParams: {
                                                idArchivo: "",
                                                idEstado: $stateParams.id_product
                                            }
                                        });
                                    }
                                }
                            });
                            this.modal.result.then(function () {
                                archivosListListener.dispatch();
                                $state.go('^');
                            }, function (reason) {
                                archivosListListener.dispatch();
                                if (reason === 'force-close')
                                    return;
                                $state.go('^');
                            });
                        }]
                });
    }

})();
