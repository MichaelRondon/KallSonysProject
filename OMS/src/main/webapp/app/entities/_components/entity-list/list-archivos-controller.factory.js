(function() {
    'use strict';
    angular
        .module('omsApp')
        .factory('ArchivosListControllerFactory', ArchivosListControllerFactory);

    ArchivosListControllerFactory.$inject = ['$filter', 'EntityListControllerFactory', '$state', 'ENTITY_STATES', 'ListWithModalListListenerImplement', '$window', 'AlertService'];

    function ArchivosListControllerFactory ($filter, EntityListControllerFactory, $state, ENTITY_STATES, ListWithModalListListenerImplement, $window, AlertService) {
        function createController($scope, pagingParams, EntityResource, parentEntityInstance, archivosListListener, inspeccionEntity, customOptions){

            var controllerOptions=angular.extend({
                title:"Archivos",
                entityName:'entity',
                listItemCreateBtnLabel:"Agregar Archivos",
                withBackButton:true,
                withBulkButton:false,
                sortable:true,
                parentEntityType:'', //TODO throw si algunas opciones no se especifican
                parentFilterParamName:'',
                
                getColumnsConfig:function(controller){
                    return {
                        fields:[
                            {
                                sortBy:'id', 
                                label:"ID", 
                                value:function(item){
                                    return item.id;
                                }
                            },
                            {
                                sortBy:'nombre', 
                                label:"Nombre de archivo", 
                                value:function(item){
                                    return item.nombre;
                                }
                            },
                            {
                                sortBy:'fechaDeRegistro', 
                                label:"Fecha De Registro", 
                                value:function(item){
                                    return $filter('date')(item.fechaDeRegistro, 'medium');
                                }
                            }
                            /*},
                            {
                                sortBy:'ubicacion', 
                                label:"Ubicacion", 
                                value:function(item){
                                    return item.ubicacion;
                                },
                                linkExternal:function(item){
                                    return{
                                        text:'Ir al archivo',
                                        url:item.ubicacion
                                    };   
                                }
                            }*/
                        ],
                        deleteLink:function(item){
                            return{
                                state:controller.getOptions().entityName+'.delete',
                                stateParams:{id_archivo:item.id}
                            };   
                        },
                        extraLink: function(item){
                            return {
                                state: controller.getOptions().entityName,
                                extraLinkLabel: 'Descargar',
                                linkButtonClasses: ['btn-primary'],
                                linkIconClasses: ['fa', 'fa-download'],
                                isVisible: function(){
                                    return true;
                                },
                                handleClick: function(){
                                    controller.downloadS3Object(item);
                                }
                            };
                        }
                    };
                }
            } || customOptions || {});
            
            var ArchivosListController=EntityListControllerFactory.create($scope, pagingParams, EntityResource, parentEntityInstance, controllerOptions);
            ArchivosListController.prototype=angular.extend(ArchivosListController.prototype,{
                postConstructor:function(){
                    if(!this.getOptions().parentEntityType){
                        throw new Error('Debes especificar la opción "parentEntityType"');
                    }
                    if(!this.getOptions().parentFilterParamName){
                        throw new Error('Debes especificar la opción "parentFilterParamName"');
                    }
                },
                transitionToCurrentState:function(){
                    var vm=this;
                    $state.go($state.$current, {
                        filesPage: vm.page,
                        filesSort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                        filesSearch: vm.currentSearch,
                        filesCount:vm.itemsPerPage
                    });
                },
                goToListItemCreate:function(){
                    var vm=this;
                    var createStateName=vm.getEntityStateNameBase()+'.upload-file';
                    $state.go(createStateName);
                },
                canCreateListItems:function(){
                    var vm=this;
                    var parentEntityInstance=vm.getParentEntity();
                    var isInspeccionEditable=true;
                    var isParentEditable=true;
                    if(inspeccionEntity){
                        var inspeccionEstado=inspeccionEntity.estado;
                        var isInspeccionFinalized=inspeccionEstado && inspeccionEstado===ENTITY_STATES.FINISH_STATE;
                        var isInspeccionDiscarded=inspeccionEstado && inspeccionEstado===ENTITY_STATES.DISCARDED;
                        isInspeccionEditable=!isInspeccionFinalized && !isInspeccionDiscarded;
                    }
                    if(parentEntityInstance){
                        var parentEntityEstado=parentEntityInstance.estado;
                        var isParentFinalized=parentEntityEstado && parentEntityEstado===ENTITY_STATES.FINISH_STATE;
                        var isParentDiscarded=parentEntityEstado && parentEntityEstado===ENTITY_STATES.DISCARDED;
                        isParentEditable=!isParentFinalized && !isParentDiscarded;
                    }
                    return isParentEditable && isInspeccionEditable;
                },

                isListItemFinalizedEntity:function(entity){
                    var vm=this;
                    var parentEntityInstance=vm.getParentEntity();
                    var isInstanceFinalized=entity.estado && entity.estado===ENTITY_STATES.FINISH_STATE;
                    var isParentFinalized=false;
                    var isInspeccionFinalized=false;
                    if(parentEntityInstance){
                        isParentFinalized=parentEntityInstance.estado && parentEntityInstance.estado===ENTITY_STATES.FINISH_STATE;
                    }
                    if(inspeccionEntity){
                        isInspeccionFinalized=inspeccionEntity.estado && inspeccionEntity.estado===ENTITY_STATES.FINISH_STATE;
                    }
                    return isInstanceFinalized || isParentFinalized || isInspeccionFinalized;
                },
                isListItemDiscardedEntity:function(entity){
                    var vm=this;
                    var parentEntityInstance=vm.getParentEntity();
                    var isInstanceDiscarded=entity.estado && entity.estado===ENTITY_STATES.DISCARDED;
                    var isParentDiscarded=false;
                    var isInspeccionDiscarded=false;
                    if(parentEntityInstance){
                        isParentDiscarded=parentEntityInstance.estado && parentEntityInstance.estado===ENTITY_STATES.DISCARDED;
                    }
                    if(inspeccionEntity){
                        isInspeccionDiscarded=inspeccionEntity.estado && inspeccionEntity.estado===ENTITY_STATES.DISCARDED;
                    }
                    return isInstanceDiscarded || isParentDiscarded || isInspeccionDiscarded;
                },
                getParentEntity:function(){
                    return parentEntityInstance;
                },
                downloadS3Object:function(entity){
                    EntityResource.download({id:entity.id}, function(file){
                        $window.saveAs(file.response, entity.nombre);
                    }, function(err){
                        AlertService.warning("Ocurrió un problema al descargar el archivo, intentelo nuevamente");
                    });
                }
            });
            ListWithModalListListenerImplement.implement(ArchivosListController, archivosListListener);
            return ArchivosListController;
        }

        return {
            create:createController
        };

    }
})();
