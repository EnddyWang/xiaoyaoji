<%--
  User: zhoujingjie
  Date: 17/4/4
  Time: 12:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="hide">
    <script src="//lib.baomitu.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://lib.baomitu.com/uikit/3.0.0-beta.39/js/uikit.min.js"></script>
    <script src="http://lib.baomitu.com/uikit/3.0.0-beta.39/js/uikit-icons.min.js"></script>
    <script src="${cdn}/assets/toastr/toastr.min.js"></script>
    <script src="http://lib.baomitu.com/pace/1.0.2/pace.min.js"></script>
    <script src="http://lib.baomitu.com/require.js/2.3.3/require.min.js"></script>
    <script>
        if(window.toastr){
            toastr.options.escapeHtml = true;
            toastr.options.closeButton = true;
            toastr.options.positionClass = 'toast-top-center';
            toastr.options.preventDuplicates=true;
        }
        window.xyj = window.xyj || {
            v:'${v}',ctx:'${ctx}',fileAccess:'${cdn}',cdn:'http://f1.xiaoyaoji.cn'
        };

        requirejs.config({
            baseUrl:'${assets}',
            urlArgs:'v=1',
            paths:{
                'vue':'http://lib.baomitu.com/vue/2.5.13/vue.min',
                'doc-common':'${assets}/js/doc/doc-common',
                'veeValidate':'${cdn}/assets/vue/vee-validate.min',
                //'vueResource':'${cdn}/assets/vue/vue.resources',
                'vueResource':'http://lib.baomitu.com/vue-resource/1.3.4/vue-resource.min',
                'vueEx':'${cdn}/assets/vue/vue.ex',
                'utils':'js/utils'
            }
        });

        $(function(){
            setTimeout(function(){
                $('.uk-icon').each(function(){
                    if($(this).find('svg').size()>1){
                        $(this).find('svg:eq(0)').remove();
                    }
                });
            },500);
        });
    </script>
</div>