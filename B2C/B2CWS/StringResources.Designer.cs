﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace B2CWS {
    using System;
    
    
    /// <summary>
    ///   A strongly-typed resource class, for looking up localized strings, etc.
    /// </summary>
    // This class was auto-generated by the StronglyTypedResourceBuilder
    // class via a tool like ResGen or Visual Studio.
    // To add or remove a member, edit your .ResX file then rerun ResGen
    // with the /str option, or rebuild your VS project.
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("System.Resources.Tools.StronglyTypedResourceBuilder", "4.0.0.0")]
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
    [global::System.Runtime.CompilerServices.CompilerGeneratedAttribute()]
    internal class StringResources {
        
        private static global::System.Resources.ResourceManager resourceMan;
        
        private static global::System.Globalization.CultureInfo resourceCulture;
        
        [global::System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1811:AvoidUncalledPrivateCode")]
        internal StringResources() {
        }
        
        /// <summary>
        ///   Returns the cached ResourceManager instance used by this class.
        /// </summary>
        [global::System.ComponentModel.EditorBrowsableAttribute(global::System.ComponentModel.EditorBrowsableState.Advanced)]
        internal static global::System.Resources.ResourceManager ResourceManager {
            get {
                if (object.ReferenceEquals(resourceMan, null)) {
                    global::System.Resources.ResourceManager temp = new global::System.Resources.ResourceManager("B2CWS.StringResources", typeof(StringResources).Assembly);
                    resourceMan = temp;
                }
                return resourceMan;
            }
        }
        
        /// <summary>
        ///   Overrides the current thread's CurrentUICulture property for all
        ///   resource lookups using this strongly typed resource class.
        /// </summary>
        [global::System.ComponentModel.EditorBrowsableAttribute(global::System.ComponentModel.EditorBrowsableState.Advanced)]
        internal static global::System.Globalization.CultureInfo Culture {
            get {
                return resourceCulture;
            }
            set {
                resourceCulture = value;
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to api/producto/buscar.
        /// </summary>
        internal static string ServicioBuscarProductos {
            get {
                return ResourceManager.GetString("ServicioBuscarProductos", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to api/producto/campanias.
        /// </summary>
        internal static string ServicioCampanias {
            get {
                return ResourceManager.GetString("ServicioCampanias", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to api/producto/historico/vendidos.
        /// </summary>
        internal static string ServicioHistoricoProductos {
            get {
                return ResourceManager.GetString("ServicioHistoricoProductos", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to api/ImageLarge.
        /// </summary>
        internal static string ServicioLocalImagenes {
            get {
                return ResourceManager.GetString("ServicioLocalImagenes", resourceCulture);
            }
        }

        /// <summary>
        ///   Looks up a localized string similar to api/producto.
        /// </summary>
        internal static string ServicioProductos
        {
            get
            {
                return ResourceManager.GetString("ServicioProductos", resourceCulture);
            }
        }

        /// <summary>
        ///   Looks up a localized string similar to api/categorias.
        /// </summary>
        internal static string ServicioCategorias
        {
            get
            {
                return ResourceManager.GetString("ServicioCategorias", resourceCulture);
            }
        }
    }
}