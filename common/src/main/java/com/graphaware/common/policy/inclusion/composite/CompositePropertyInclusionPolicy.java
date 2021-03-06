/*
 * Copyright (c) 2013-2018 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.common.policy.inclusion.composite;

import com.graphaware.common.policy.inclusion.PropertyInclusionPolicy;
import org.neo4j.graphdb.Entity;

import java.util.Arrays;

/**
 * {@link PropertyInclusionPolicy} composed of multiple other policies. All contained policies must "vote"
 * <code>true</code> to {@link #include(String, org.neo4j.graphdb.Entity)} in order for this policy to
 * return <code>true</code>.
 */
public abstract class CompositePropertyInclusionPolicy<T extends Entity> implements PropertyInclusionPolicy<T> {

    private final PropertyInclusionPolicy<T>[] policies;

    protected CompositePropertyInclusionPolicy(PropertyInclusionPolicy<T>[] policies) {
        if (policies == null || policies.length < 1) {
            throw new IllegalArgumentException("There must be at least one wrapped policy in composite policy");
        }
        for (PropertyInclusionPolicy<T> p : policies) {
            if (p == null) {
                throw new IllegalArgumentException("Policy must not be null");
            }
        }
        this.policies = policies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean include(String key, T object) {
        for (PropertyInclusionPolicy<T> policy : policies) {
            if (!policy.include(key, object)) {
                return false;
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompositePropertyInclusionPolicy that = (CompositePropertyInclusionPolicy) o;

        if (!Arrays.equals(policies, that.policies)) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(policies);
    }
}
