/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.max.internal.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.net.util.Base64;
import org.openhab.binding.max.internal.Utils;

/**
 * The {@link T_CubeCommand} is used to unlink MAX! devices from the Cube.
 *
 * @author Marcel Verpaalen - Initial Contribution
 * @since 2.0
 *
 */

public class T_Command extends CubeCommand {

    private static final int FORCE_UPDATE = 1;
    private static final int NO_FORCE_UPDATE = 0;

    private final List<String> rfAddresses = new ArrayList<>();
    private final boolean forceUpdate;

    public T_Command(String rfAddress, boolean forceUpdate) {
        this.rfAddresses.add(rfAddress);
        this.forceUpdate = forceUpdate;
    }

    /**
     * Adds a rooms for deletion
     */
    public void addRoom(String rfAddress) {
        this.rfAddresses.add(rfAddress);
    }

    @Override
    public String getCommandString() {
        final int updateForced = forceUpdate ? FORCE_UPDATE : NO_FORCE_UPDATE;
        byte[] commandArray = null;
        for (String rfAddress : rfAddresses) {
            commandArray = ArrayUtils.addAll(Utils.hexStringToByteArray(rfAddress), commandArray);
        }
        String encodedString = Base64.encodeBase64StringUnChunked(commandArray);

        return "t:" + String.format("%02d", rfAddresses.size()) + "," + updateForced + "," + encodedString + '\r'
                + '\n';
    }

    @Override
    public String getReturnStrings() {
        return "A:";
    }

}
