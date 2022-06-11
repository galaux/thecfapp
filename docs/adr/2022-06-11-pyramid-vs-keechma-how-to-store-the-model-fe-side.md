## 2022-06-11 re-think pyramid vs keechma-db

I am finishing a commit so that re-frame/db would store namespaced entities just as they are stored in Datomic. The initial reason for me doing this was that I was annoyed by all entities being stored in re-frame under key `:id` and thought that having them stored separately under `:user/id`, `:community/id` etc would bring me more power to retrieve them. I could for instance retrieve the list of all communities stored in the FE.

Said commit is going well: I'm using a `defmulti` which does the job rather simply. I'm almost done (changing the UI to read now namespaced entities) but it now appears to me this approach has downsides:
- though the `defmulti` works well, I need to do this parsing myself manually. Every time I'll add an entity or entity relationships, I'll have to write such `defmethod`
- this forces me to request GraphQL's `__typename` on all entities for all queries of the app
- then I'm thinking about adding a _backref_ (references inside entities to the owning entities so that a community has wods … which themselves know about the owning community) which will add one more layer of complexity to handle manually inside these `defmethod`s.

Some of Keechma's feature (to which I had previously preferred Pyramid) now look more interesting to me:
- once you write a schema, Keechma indexes all entities described in the GraphQL response
- the schema allows finding entities in nested structures such as pagination

### Some comparison between Pyramid and Keechma

- pyramid has the advantage of walking what you `add` and indexing any map that
  has an `id` where keechma doesn't **by default**. In keechma, one needs to
  define a schema for nested entities to be indexed.
- as already stated in my previous evaluation of both libs, keechma has multiple functions (`insert-named` an `insert-entity`) where pyramid uses only one (`add` takes either an entity or a non-entities)
- as also already stated in my previous evaluation of the libs, pyramid + a _camel snake kebab_ transformation does _most_ of the job keechma does with its schema
- but keechma's schema still gives some advantage though: it can parse data that is arranged in a custom way such as when result is paginated. In the README example, the author parses the result of a paginated GraphQL result! That would be handy should I want to support pagination … and I most sureley will at some point!
  - if keechma allows easy handling of pagination that would be a great plus. But it also depends on how pagination is (or is not) handled by Lacinia. I can't find any ref to pagination in Lacinia's README. Does it support [Relay](https://relay.dev/) like pagination at all?
- anyway, none will give me the feature of _backref_ (automagically have child entity know about its parent entity such as when I get a community with its wods, having each `wod.community` set to the owning community)
- keechma has an interesting feature `reverse-include` where one can get an entity pointing at another

### Question

Should I go for
- Pyramid without namespaced entities
- Pyramid with namespaced entities (that could make things complicated if I want to switch to Keechma)
- Keechma

I'm not even sure anymore having namespaced entities would bring anything: in Pyramid, that would allow me to retrieve the list of entities of one type but I don't need this at the moment (YAGNI)

### Decision

Keep Pyramid **without** namespaced entities: that works at the moment and I don't have any problem that namespacing or switching to Keechma will solve (_« Don't try to fix something that is not broken »_)
Remove the current commit that is adding namespaces to entities but keep a reference (7d69ff9) to it just in case
