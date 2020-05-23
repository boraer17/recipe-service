package com.rs.data.load.deserializer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rs.data.DeserializerType;
import com.rs.exception.DeserializationException;
import com.rs.model.Head;
import com.rs.model.Ingredient;
import com.rs.model.IngredientDivision;
import com.rs.model.Recipe;
import com.rs.model.RecipeDivision;
import com.rs.model.RecipeRaw;
import com.rs.model.Step;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Qualifier(value = "xmlDeserializer")
public class CustomXmlDeserializer implements ICustomDeserializer {

	@Override
	public DeserializerType getType() {
		return DeserializerType.XML;
	}

	@Override
	public RecipeDivision process(Message<String> msg) throws DeserializationException {
		return deserialize(msg.getPayload());
	}

	ObjectMapper objectMapper = new ObjectMapper();

	private static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}

	public RecipeDivision deserialize(String content) throws DeserializationException {
		try {
			final XmlMapper xmlMapper = new XmlMapper();
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder dBuilder = factory.newDocumentBuilder();
			final InputStream targetStream = new ByteArrayInputStream(content.getBytes());
			final Document doc = dBuilder.parse(targetStream);

			doc.getDocumentElement().normalize();
			log.info("Root element: " + doc.getDocumentElement().getNodeName());
			final NodeList nIngDivList = doc.getElementsByTagName(IngredientDivision.TAG);

			if (nIngDivList.getLength() != 0) {
				return parseRecipeDivision(doc, xmlMapper);

			} else {
				final RecipeRaw recipeRaw = xmlMapper.readValue(content, RecipeRaw.class);
				final IngredientDivision ingredientdivs = new IngredientDivision("",
						recipeRaw.getRecipe().getIngredients());
				RecipeDivision recipeDivision = new RecipeDivision(recipeRaw.getRecipe().getHead(),
						Collections.singleton(ingredientdivs), recipeRaw.getRecipe().getDirections());
				return recipeDivision;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DeserializationException(e.getMessage());
		}
	}

	private static RecipeDivision parseRecipeDivision(final Document doc, final XmlMapper xmlMapper) throws Exception {

		final NodeList nHeadList = doc.getElementsByTagName(Head.TAG);
		final NodeList nDirectionList = doc.getElementsByTagName(Recipe.DIRECTION_TAGS);
		final NodeList nIngDivList = doc.getElementsByTagName(IngredientDivision.TAG);
		Node nNode = nHeadList.item(0);

		log.info("Current Element: " + nNode.getNodeName());

		final String sHead = nodeToString(nNode);
		Head head = xmlMapper.readValue(sHead, Head.class);

		nNode = nDirectionList.item(0);
		log.info("Current Element: " + nNode.getNodeName());

		final String sDirection = nodeToString(nNode);
		Step step = xmlMapper.readValue(sDirection, Step.class);

		Set<IngredientDivision> ingSet = new HashSet<IngredientDivision>();
		for (int i = 0; i < nIngDivList.getLength(); i++) {
			nNode = nIngDivList.item(i);
			IngredientDivision ingredientDivision = new IngredientDivision();
			Set<Ingredient> ingredients = new HashSet<Ingredient>();
			log.info("Current Element: " + nNode.getNodeName());
			NodeList childs = nNode.getChildNodes();
			for (int j = 0; j < childs.getLength(); j++) {
				Node nChild = childs.item(j);
				final String sChild = nodeToString(nChild);
				if (nChild.getAttributes() != null) {
					if (nChild.getNodeName().equals("title")) {
						ingredientDivision.setTitle(childs.item(j).getTextContent());
					} else {
						final Ingredient ingredient = xmlMapper.readValue(sChild, Ingredient.class);
						ingredients.add(ingredient);
					}
				}
			}
			ingredientDivision.setIngredients(ingredients);
			ingSet.add(ingredientDivision);
		}
		return new RecipeDivision(head, ingSet, Collections.singleton(step));

	}

}
