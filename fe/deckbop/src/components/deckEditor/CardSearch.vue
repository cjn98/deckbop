<template>
    <div class="card-search">
       <sidebar-nav></sidebar-nav>
        <div>
            <form class="column-content" id="search-form" > 
                <input type="text" v-model="searchText" placeholder="Search">
                <button v-on:click.prevent="search()">search</button>
            </form>
        </div>
    </div>    
</template>

<script>
import SidebarNav from './SidebarNav.vue'
import { getSearchUrl } from '../../config/scryfall'
export default {
  components: { SidebarNav },
    name: "CardSearch",
    data: () => {
        return {
            searchText: ''
        }
    },
    methods: {
        search: function(){
            const vm = this
            const reqUrl = getSearchUrl(vm.searchText)
            this.$http.get(reqUrl)
            .then((req) => {
                this.$store.dispatch('SET_SEARCH_RESULTS', req.data)
            })
            .catch((err) => {
                console.log("search error: ", err)
            })
        },
        
    },
}
</script>

<style scoped>
    @import "../../style/style.css";
    

    
    
</style>