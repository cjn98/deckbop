<template>
    <div>
        <div v-if="showLoggedInView" class="new-deck">
            <router-link to="/deckEditor" @click.native="createNewDeck">
                <h1>+ New Deck</h1>
            </router-link>
        </div>
        <div class="view-decks">
            <div v-for="(deck, index) in getSortedUserDecks" :key="index">
                <DeckBox v-bind:deck="deck" />
            </div>
        </div>    
    </div>
    
</template>

<script>
import DeckBox from "./DeckBox";
export default {
    name: "ViewDecks",
    components: { DeckBox },
    methods: {
        createNewDeck() {
            this.$store.dispatch('CLEAR_DECK')
            this.$store.dispatch('SET_EDITOR_MODE', 'create')
        },

    },
    computed: {
        showLoggedInView() {
            return !!this.$store.getters.user.token
        },
        getSortedUserDecks() {
            return [... this.$store.getters.user.decks].sort((a, b) => (a.name > b.name) ? 1 : -1)
        }
    }
}
</script>

<style scoped>
    div {
        padding-top: 3em;
    }
    .view-decks {
        display: flex;
        flex-flow: row wrap;
        justify-content: center;
    }
    .view-decks > * {
        padding: 0.5rem;
    }
    .new-deck {
        padding-left: 2em;
        flex: 1;
    }
</style>


