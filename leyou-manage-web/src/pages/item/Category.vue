<template>
  <v-card>
      <v-flex xs12 sm10>
        <v-tree url="/item/category/list"
                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>
  import {treeData} from "../../mockDB";

  export default {
    name: "category",
    data() {
      return {
        isEdit:true,
        treeData:treeData
      }
    },
    methods: {
      handleAdd(node) {
        console.log("add .... ");
        console.log(node);
        // this.$http.post("/item/category/addCategory",node)
        //   .then((res)=>{
        //     alert("添加成功");
        //   }
        //
        // ).catch((error)=>{
        //   alert("添加失败")
        // })
      },
      handleEdit(node) {
        //console.log("edit... id: " + id + ", name: " + name)
        console.log("------------------------------");
        console.log(node);
        console.log("------------------------------");

        let id=node.id;
        if(id==0){
          //添加
          this.$http.post("/item/category/add",node).then(
            (res)=>{

              alert(res.data+"添加成功")
              window.location.reload();
            }
          ).catch(err=>{
            alert("添加失败了！！")
          })
        }else{
          //修改
          this.$http.post("/item/category/update",node).then(
            (res)=>{
              if(res.data=="SUCC"){
                alert(res.data+"修改成功")
              }else if(res.data=="FAIL"){
                alert(res.data)
                alert("修改失败")
              }
              window.location.reload();
            }
          ).catch(err=>{
            alert("修改失败了！！")
          })
        }
        /*if(node.id==0){//添加
          this.$http.post("/item/category/addCategory",node)
            .then((res)=>{
              node.id = res.data
              }

            ).catch((error)=>{
            console.log(error);
            alert("添加失败")
          })
        }else{//修改

          this.$http.post("/item/category/editCategory",node)
            .then((res)=>{
              }

            ).catch((error)=>{
            alert("修改失败")
          })
        }*/

      },
      handleDelete(id) {
        console.log("delete ... " + id)

       /* this.$http.post("/item/category/deleteById",{
          id:id
        }).then((res)=>{
            }
          ).catch((error)=>{
          alert("删除失败")
        })*/
       this.$http.get("/item/category/deleteById",{params:{id:id}})
         .then((res)=>{
           if(res.data=="SUCC"){
             alert(res.data+"删除成功")
           }else if(res.data=="FAIL"){
             alert(res.data)
             alert("删除失败")
           }
         }).catch((err)=>{
           alert("请求失败")
        })
      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>
