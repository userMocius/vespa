// Copyright Yahoo. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.

#pragma once

namespace search::tensor {

/**
 * Interface that is used to load a nearest neighbor index from binary form.
 */
class NearestNeighborIndexLoader {
public:
    virtual ~NearestNeighborIndexLoader() {}

    /**
     * Loads the next part of the index (e.g. the node corresponding to a given document)
     * and returns whether there is more data to load.
     *
     * This might throw vespalib::IoException.
     */
    virtual bool load_next() = 0;
};

}
