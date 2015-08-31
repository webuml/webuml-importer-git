package com.webuml.importer.domain.primitives;

public class ElementIdConverter {
//    implements GenericConverter {
//
//  @Override
//  public Set<ConvertiblePair> getConvertibleTypes() {
//    Set<ConvertiblePair> convertiblePairs = new HashSet<>(2);
//    convertiblePairs.add(new ConvertiblePair(ElementId.class, DBObject.class));
//    convertiblePairs.add(new ConvertiblePair(DBObject.class, ElementId.class));
//    return convertiblePairs;
//  }
//
//  @Override
//  public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//    if (isVorgangId(sourceType)) {
//      if (isNull(source)) {
//        return null;
//      }
//      return getValue(source);
//    }
//    else if (isObjectId(sourceType)) {
//      return source == null ? null : new ElementId(source.toString());
//    }
//    throw new RuntimeException("Unsupported source type: " + sourceType.getType());
//  }
//
//  private boolean isNull(Object source) {
//    return source == null || getValue(source) == null;
//  }
//
//  private boolean isVorgangId(TypeDescriptor sourceType) {
//    return ElementId.class.isAssignableFrom(sourceType.getType());
//  }
//
//  private boolean isObjectId(TypeDescriptor sourceType) {
//    return ObjectId.class.isAssignableFrom(sourceType.getType());
//  }
//
//  private String getValue(Object source) {
//    return ((ElementId) source).getValue();
//  }
}