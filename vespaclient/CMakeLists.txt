# Copyright Vespa.ai. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
vespa_define_module(
    DEPENDS
    vespadefaults
    vespa_configdefinitions
    vespa_config
    vespalog
    vespa_document
    vespa_documentapi
    vespalib

    LIBS
    src/vespa/vespaclient/clusterlist

    APPS
    src/vespa/vespaclient/vesparoute
)
