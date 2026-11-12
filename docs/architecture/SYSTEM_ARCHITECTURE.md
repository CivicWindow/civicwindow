# CivicWindow system architecture

**Status:** Proposed runtime boundary, not implemented code.

Government-data connectors retrieve records from validated sources. Normalization maps source-specific material to a common civic-record contract while retaining original identifiers, dates, retrieval status and official evidence links. Local storage holds downloaded records and private interests. On-device classification and semantic matching produce labeled outputs and scores. Application control logic combines outputs, checks evidence references and presents accepted or uncertain results. Optional notifications follow successful retrieval and matching. User-directed Evidence Packs export selected records and labeled model outputs.

Training and model evaluation occur in the development environment; core inference runs on the Android device. Fresh records need connectivity, but downloaded records can be inspected offline. Source evidence shows where displayed material came from; it does not prove a government's record is correct or that all relevant records were found.

The role source areas are AI/ML (`modules/ai`, `ml`), Android/Product (`app`, `modules/android`) and Data/Platform (`modules/data`, `connectors`). Shared contracts link them. Actual library, runtime, model and persistence choices remain to be measured or approved.
