(function(){
    define(function(){
        return {
            pushMessage:function(method, args){
                window.postMessage({type: 'event', method: method, args: args}, '*')
            },
            onMessage:function(type,callback,self){
                window.addEventListener('message',function(e){
                    if(e.data && e.data.type==='event'){
                        callback.apply(self,e.data.args);
                    }
                });
            }
        }
    });
})();