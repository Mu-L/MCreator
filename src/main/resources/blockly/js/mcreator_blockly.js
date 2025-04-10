let global_variables = [];

Blockly.HSV_SATURATION = MCR_BLOCKLY_PREF['saturation'];
Blockly.HSV_VALUE = MCR_BLOCKLY_PREF['value'];

const blockly = document.getElementById('blockly');
const workspace = Blockly.inject(blockly, {
    media: 'res/',
    oneBasedIndex: false,
    sounds: false,
    comments: MCR_BLOCKLY_PREF['comments'],
    collapse: MCR_BLOCKLY_PREF['collapse'],
    disable: false,
    trashcan: MCR_BLOCKLY_PREF['trashcan'],
    renderer: MCR_BLOCKLY_PREF['renderer'],
    zoom: {
        controls: false,
        wheel: true,
        startScale: 0.95,
        maxScale: MCR_BLOCKLY_PREF['maxScale'],
        minScale: MCR_BLOCKLY_PREF['minScale'],
        scaleSpeed: MCR_BLOCKLY_PREF['scaleSpeed']
    },
    toolbox: '<xml id="toolbox"><category name="" colour=""></category></xml>'
});

workspace.addChangeListener(function (event) {
    if (workspace.isDragging())
        return; // Don't update while changes are happening.

    if (event.isUiEvent)
        return; // Don't update on UI-only events.

    if (typeof javabridge !== "undefined")
        javabridge.triggerEvent();
});

window.addEventListener('resize', function () {
    Blockly.svgResize(workspace);
});
Blockly.svgResize(workspace);

// disable help entry
Blockly.Block.prototype.setHelpUrl = function () {
    return '';
}

// modify blockly to export all variables, not only used ones
Blockly.Variables.allUsedVarModels = function () {
    return workspace.getVariableMap().getAllVariables();
};

Blockly.ContextMenuRegistry.registry.register({
    displayText: function () {
        return javabridge.t("blockly.context_menu.cleanup_unused_blocks");
    },
    preconditionFn: function (scope) {
        if (scope.workspace.getTopBlocks().length > 1) {
            return 'enabled';
        }
        return 'hidden';
    },
    callback: function (scope) {
        const group = Blockly.Events.getGroup();
        Blockly.Events.setGroup(true);
        for (const block of scope.workspace.getTopBlocks()) {
            if (block.type !== javabridge.startBlockForEditor(editorType))
                block.dispose();
        }
        Blockly.Events.setGroup(group);
    },
    scopeType: Blockly.ContextMenuRegistry.ScopeType.WORKSPACE,
    id: 'cleanupUnusedBlocks'
});

function getVariablesOfType(type) {
    let retval = [];

    workspace.getVariableMap().getAllVariables().forEach(function (v) {
        if (v.type === type)
            retval.push(["Local: " + v.name, "local:" + v.name]);
    });

    global_variables.forEach(function (v) {
        if (v.type === type)
            retval.push(["Global: " + v.name, "global:" + v.name]);
    });

    if (retval.length > 0)
        return retval;
    else
        return [["", ""]];
}

function getSerializedLocalVariables() {
    let retval = "";
    workspace.getVariableMap().getAllVariables().forEach(function (v, index, array) {
        retval += ((v.name + ";" + v.type) + (index < array.length - 1 ? ":" : ""));
    });
    return retval;
}

function arrayToBlocklyDropDownArray(arrorig) {
    let retval = [];
    arrorig.forEach(function (element) {
        retval.push(["" + element, "" + element]);
    });
    return retval;
}

// A function to properly convert workspace to XML (google/blockly#6738)
function workspaceToXML() {
    const treeXml = Blockly.Xml.workspaceToDom(workspace, true);

    // Remove variables child if present
    const variablesElements = treeXml.getElementsByTagName("variables");
    for (const varEl of variablesElements) {
        treeXml.removeChild(varEl);
    }

    // Add variables child on top of DOM
    const variablesElement = Blockly.Xml.variablesToDom(workspace.getAllVariables());
    if (variablesElement.hasChildNodes()) {
        treeXml.prepend(variablesElement);
    }

    return Blockly.Xml.domToText(treeXml);
}